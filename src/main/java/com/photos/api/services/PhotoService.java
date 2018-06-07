package com.photos.api.services;

import com.photos.api.models.Photo;
import com.photos.api.models.User;
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

@Transactional
@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AmazonClient amazonClient;


    public List<Photo> getAll() {
        return photoRepository.findAll();
    }

    public Photo getById(final Long id) {
        //TODO: Walidacja czy możemy pobrać tę kategorię
        Optional<Photo> photo = photoRepository.findById(id);

        if (!photo.isPresent()) {
            throw new IllegalArgumentException(String.format("Photo with ID %d not found.", id));
        }

        return photo.get();
    }

    public Photo add(MultipartFile file, String description) {
        {

            User user = userService.getCurrent();
            String photoPath = this.amazonClient.uploadFile(file, user.getUuid());

            Photo photo = new Photo();
            photo.setName(file.getOriginalFilename());
            photo.setPath(photoPath);
            photo.setDescription(description);
            photo.setUser(user);

            try {
                photo = photoRepository.save(photo);
            } catch (Exception e) {
                this.amazonClient.deleteFile(photoPath);
            }

            return photo;
        }
    }

    public Photo update(final Photo photo) {
        //TODO: Walidacja czy możemy aktualizować tę kategorię
        return photoRepository.save(photo);
    }

    public void delete(final Long id) {
        //TODO: Walidacja czy możemy usunąć tę kategorię
        photoRepository.deleteById(id);
    }
}
