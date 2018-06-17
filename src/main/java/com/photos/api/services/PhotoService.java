package com.photos.api.services;

import com.photos.api.exceptions.*;
import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.Tag;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoVisibility;
import com.photos.api.models.enums.UserRole;
import com.photos.api.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Micha Królewski on 2018-04-14.
 * @version 1.0
 */

@Service
@Transactional
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AmazonService amazonService;

    public List<Photo> getAll() {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAll();
        }

        return photoRepository.findAllByUserOrVisibility(currentUser, PhotoVisibility.PUBLIC);
    }

    public List<Photo> getAllByCategory(Category category) {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByCategories(category);
        }

        return photoRepository.findAllByCategoriesAndVisibilityOrCategoriesAndUser(
                category, PhotoVisibility.PUBLIC,
                category, currentUser
        );
    }

    public List<Photo> getAllByTag(Tag tag) {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByTags(tag);
        }

        return photoRepository.findAllByTagsAndVisibilityOrTagsAndUser(
                tag, PhotoVisibility.PUBLIC,
                tag, currentUser
        );
    }

    public List<Photo> getAllByUser(User user) {
        User currentUser = userService.getCurrent();

        if (user == currentUser || currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByUser(user);
        }

        return photoRepository.findAllByUserAndVisibility(user, PhotoVisibility.PUBLIC);
    }

    public Photo getById(final Long id) throws EntityNotFoundException, EntityGetDeniedException {
        Optional<Photo> photoOptional = photoRepository.findById(id);

        if (!photoOptional.isPresent()) {
            throw new EntityNotFoundException();
        }

        Photo photo = photoOptional.get();

        if (photo.getVisibility() == PhotoVisibility.PRIVATE && photo.getUser() != userService.getCurrent() && userService.getCurrent().getRole() != UserRole.ADMIN) {
            throw new EntityGetDeniedException();
        }

        return photo;
    }

    public Photo add(MultipartFile file, String description) {
        User user = userService.getCurrent();

        String photoPath = this.amazonService.uploadFile(file, user.getUuid());

        Photo photo = new Photo();
        photo.setName(file.getOriginalFilename());
        photo.setPath(photoPath);
        photo.setUrl(this.amazonService.getFileUrl(photoPath));
        photo.setDescription(description);
        photo.setUser(user);

        try {
            photo = photoRepository.save(photo);
        } catch (Exception e) {
            this.amazonService.deleteFile(photoPath);
        }

        return photo;
    }

    public Photo update(final Photo photo) throws EntityNotFoundException, EntityUpdateDeniedException, EntityOwnerChangeDeniedException {
        Photo currentPhoto;

        try {
            currentPhoto = this.getById(photo.getId());
        } catch (EntityGetDeniedException e) {
            throw new EntityUpdateDeniedException();
        }

        if (currentPhoto.getUser() != userService.getCurrent() && userService.getCurrent().getRole() != UserRole.ADMIN) {
            throw new EntityUpdateDeniedException();
        }

        if (currentPhoto.getUser() != photo.getUser() && userService.getCurrent().getRole() != UserRole.ADMIN) {
            throw new EntityOwnerChangeDeniedException();
        }

        return photoRepository.save(photo);
    }

    public void delete(final Long id) throws EntityNotFoundException, EntityDeleteDeniedException {
        Photo photo;

        try {
            photo = this.getById(id);
        } catch (EntityGetDeniedException e) {
            throw new EntityDeleteDeniedException();
        }

        if (photo.getUser() != userService.getCurrent()) {
            throw new EntityDeleteDeniedException();
        }

        this.amazonService.deleteFile(photo.getPath());

        photoRepository.deleteById(id);
    }
}
