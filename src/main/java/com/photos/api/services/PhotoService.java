package com.photos.api.services;

import com.photos.api.exceptions.*;
import com.photos.api.models.*;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.PhotoVisibility;
import com.photos.api.models.enums.UserRole;
import com.photos.api.repositories.LikeRepository;
import com.photos.api.repositories.PhotoRepository;
import com.photos.api.repositories.ShareRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private ShareRepository shareRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AmazonService amazonService;

    public List<Photo> getAllActive() {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByState(PhotoState.ACTIVE);
        }

        return photoRepository.findAllByVisibilityAndStateOrUserAndStateOrShares_UserAndState(
                PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                currentUser, PhotoState.ACTIVE,
                currentUser, PhotoState.ACTIVE
        );
    }

    public List<Photo> getAllActiveOrderedByCreationDate() {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByStateOrderByCreationDateDesc(PhotoState.ACTIVE);
        }

        return photoRepository.findAllByVisibilityAndStateOrUserAndStateOrShares_UserAndStateOrderByCreationDateDesc(
                PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                currentUser, PhotoState.ACTIVE,
                currentUser, PhotoState.ACTIVE
        );
    }

    public List<Photo> getAllActiveOrderedByLikes() {
        User currentUser = userService.getCurrent();
        List<Photo> photos;

        if (currentUser.getRole() == UserRole.ADMIN) {
            photos = photoRepository.findAllByState(PhotoState.ACTIVE);
        }

        photos = photoRepository.findAllByVisibilityAndStateOrUserAndStateOrShares_UserAndState(
                PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                currentUser, PhotoState.ACTIVE,
                currentUser, PhotoState.ACTIVE
        );

        photos.sort((a, b) -> b.getLikes().size() - a.getLikes().size());

        return photos;
    }

    public List<Photo> getAllActiveNewerThanOrderedByLikes(Date date) {
        User currentUser = userService.getCurrent();
        List<Photo> photos;

        if (currentUser.getRole() == UserRole.ADMIN) {
            photos = photoRepository.findAllByStateAndCreationDateGreaterThan(PhotoState.ACTIVE, date);
        }

        photos = photoRepository.findAllByVisibilityAndStateAndCreationDateGreaterThanOrUserAndStateAndCreationDateGreaterThanOrShares_UserAndStateAndCreationDateGreaterThan(
                PhotoVisibility.PUBLIC, PhotoState.ACTIVE, date,
                currentUser, PhotoState.ACTIVE, date,
                currentUser, PhotoState.ACTIVE, date
        );

        photos.sort((a, b) -> b.getLikes().size() - a.getLikes().size());

        return photos;
    }

    public List<Photo> getAllActiveByCategory(Category category) {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByCategoriesAndState(category, PhotoState.ACTIVE);
        }

        return photoRepository.findAllByCategoriesAndVisibilityAndStateOrCategoriesAndUserAndStateOrCategoriesAndShares_UserAndState(
                category, PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                category, currentUser, PhotoState.ACTIVE,
                category, currentUser, PhotoState.ACTIVE
        );
    }

    public Set<Photo> getAllActiveMatchingAnyOfCategories(List<Category> categories) {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findDistinctByCategoriesInAndState(categories, PhotoState.ACTIVE);
        }

        return photoRepository.findDistinctByCategoriesInAndVisibilityAndStateOrCategoriesInAndUserAndStateOrCategoriesInAndShares_UserAndState(
                categories, PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                categories, currentUser, PhotoState.ACTIVE,
                categories, currentUser, PhotoState.ACTIVE
        );
    }

    public Set<Photo> getAllActiveMatchingAllOfCategories(List<Category> categories) {
        User currentUser = userService.getCurrent();
        Set<Photo> photos;

        if (currentUser.getRole() == UserRole.ADMIN) {
            photos = photoRepository.findDistinctByCategoriesInAndState(categories, PhotoState.ACTIVE);
        } else {
            photos = photoRepository.findDistinctByCategoriesInAndVisibilityAndStateOrCategoriesInAndUserAndStateOrCategoriesInAndShares_UserAndState(
                    categories, PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                    categories, currentUser, PhotoState.ACTIVE,
                    categories, currentUser, PhotoState.ACTIVE
            );
        }

        return photos.stream().filter(photo -> photo.getCategories().containsAll(categories)).collect(Collectors.toSet());
    }

    public Set<Photo> getAllActiveMatchingAnyOfCategoriesOrderedByCreationDate(List<Category> categories) {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findDistinctByCategoriesInAndState(categories, PhotoState.ACTIVE);
        }

        return photoRepository.findDistinctByCategoriesInAndVisibilityAndStateOrCategoriesInAndUserAndStateOrCategoriesInAndShares_UserAndStateOrderByCreationDateDesc(
                categories, PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                categories, currentUser, PhotoState.ACTIVE,
                categories, currentUser, PhotoState.ACTIVE
        );
    }

    public Set<Photo> getAllActiveMatchingAllOfCategoriesOrderedByCreationDate(List<Category> categories) {
        User currentUser = userService.getCurrent();
        Set<Photo> photos;

        if (currentUser.getRole() == UserRole.ADMIN) {
            photos = photoRepository.findDistinctByCategoriesInAndState(categories, PhotoState.ACTIVE);
        } else {
            photos = photoRepository.findDistinctByCategoriesInAndVisibilityAndStateOrCategoriesInAndUserAndStateOrCategoriesInAndShares_UserAndStateOrderByCreationDateDesc(
                    categories, PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                    categories, currentUser, PhotoState.ACTIVE,
                    categories, currentUser, PhotoState.ACTIVE
            );
        }

        return photos.stream().filter(photo -> photo.getCategories().containsAll(categories)).collect(Collectors.toSet());
    }

    public List<Photo> getAllActiveByTag(Tag tag) {
        User currentUser = userService.getCurrent();

        if (currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByTagsAndState(tag, PhotoState.ACTIVE);
        }

        return photoRepository.findAllByTagsAndVisibilityAndStateOrTagsAndUserAndStateOrTagsAndShares_UserAndState(
                tag, PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                tag, currentUser, PhotoState.ACTIVE,
                tag, currentUser, PhotoState.ACTIVE
        );
    }

    public List<Photo> getAllActiveByUser(User user) {
        User currentUser = userService.getCurrent();

        if (user == currentUser || currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByUserAndState(user, PhotoState.ACTIVE);
        }

        return photoRepository.findAllByUserAndVisibilityAndStateOrUserAndShares_UserAndState(
                user, PhotoVisibility.PUBLIC, PhotoState.ACTIVE,
                user, currentUser, PhotoState.ACTIVE
        );
    }

    public List<Photo> getAllArchivedByUser(User user) throws EntityGetDeniedException {
        User currentUser = userService.getCurrent();

        if (user == currentUser || currentUser.getRole() == UserRole.ADMIN) {
            return photoRepository.findAllByUserAndState(user, PhotoState.ARCHIVED);
        }

        throw new EntityGetDeniedException();
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
        photo.setName(FilenameUtils.getBaseName(file.getOriginalFilename()));
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

    public Photo update(final Photo photo) throws EntityNotFoundException, EntityUpdateDeniedException, EntityOwnerChangeDeniedException, EntityOwnerInvalidException {
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

        for (Category category : photo.getCategories()) {
            if (category.getUser() != userService.getCurrent()) {
                throw new EntityOwnerInvalidException();
            }
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

    public Share share(final Long id, final Share share) throws EntityNotFoundException, PhotoShareDeniedException, PhotoShareTargetInvalidException {
        Photo currentPhoto;

        try {
            currentPhoto = this.getById(id);
        } catch (EntityGetDeniedException e) {
            throw new PhotoShareDeniedException();
        }

        if (currentPhoto.getUser() != userService.getCurrent()) {
            throw new PhotoShareDeniedException();
        }

        if (share.getUser() == userService.getCurrent()) {
            throw new PhotoShareTargetInvalidException();
        }

        return shareRepository.save(share);
    }

    public Like like(final Long id) throws EntityNotFoundException, PhotoLikeDeniedException {
        Photo photo;

        try {
            photo = this.getById(id);
        } catch (EntityGetDeniedException e) {
            throw new PhotoLikeDeniedException();
        }

        Like like = new Like(userService.getCurrent(), photo);

        return likeRepository.save(like);
    }
}
