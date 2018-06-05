package com.photos.api.repositories;

import com.photos.api.models.Photo;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.PhotoVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@Component
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByIdAndStateAndVisibility(Long id, PhotoState state, PhotoVisibility visibility);

    Photo findByIdAndUser(Long id, User user);

    Photo findByIdAndStateAndVisibilityAndUser(Long id, PhotoState active, PhotoVisibility aPrivate, User owner);
}
