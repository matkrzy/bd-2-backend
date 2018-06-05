package com.photos.api.services;

import com.photos.api.models.*;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.PhotoVisibility;
import com.photos.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

import static com.photos.api.services.ImageService.UPLOAD_ROOT;

/**
 * @author Micha Królewski on 2018-04-14.
 * @version 1.0
 */

@Transactional
@Service
public class PhotoService {
    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    public Photo add(final Photo photo) {
        //TODO: Walidacja przesłanych danych
        return photoRepository.save(photo);
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
