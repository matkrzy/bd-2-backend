package com.photos.api.services;

import com.photos.api.models.Photo;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.ShareState;
import com.photos.api.models.repositories.PhotoRepository;
import com.photos.api.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Micha Królewski on 2018-04-26.
 * @version 1.0
 */

@Service
public class ImageService {

    public static String UPLOAD_ROOT = System.getProperty("user.dir") + "\\..\\imageStore";
    private final ResourceLoader resourceLoader;
    private final PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    public ImageService(ResourceLoader resourceLoader, PhotoRepository photoRepository) {
        this.resourceLoader = resourceLoader;
        this.photoRepository = photoRepository;
    }


    public Resource findImage(Long id) {
        Photo photo = photoRepository.findByPhotoIDAndPhotoStateAndShareState(id, PhotoState.ACTIVE, ShareState.PUBLIC);

        if (photo == null) {
            String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            User user = userRepository.findByEmail(email);
            photo = photoRepository.findByPhotoIDAndPhotoStateAndShareStateAndOwner(id, PhotoState.ACTIVE, ShareState.PRIVATE, user);
            if (photo == null) {
                return null;
            }
        }
        return resourceLoader.getResource("file:" + UPLOAD_ROOT + "\\" + photo.getowner_email() + "\\" + photo.getPhotoID() + ".jpg");
    }

    public boolean createImage(MultipartFile file, Long id) {

        if (!file.isEmpty()) {
            try {

                String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
                User user = userRepository.findByEmail(email);
                String name = file.getOriginalFilename();
                Photo photo = photoRepository.findByPhotoIDAndOwner(id, user);
                if (photo.getPath() != null) {
                    return false;
                }
                photo.setPath(id.toString());
                photoRepository.save(photo);

                Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT + "\\" + email + "\\", id.toString() + ".jpg"));
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }


    public void deleteImage(String filename) throws IOException {

        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Files.deleteIfExists(Paths.get(UPLOAD_ROOT + "\\" + email + "\\", filename));
    }
}
