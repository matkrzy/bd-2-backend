package com.photos.api.repositories;

import com.photos.api.models.Like;
import com.photos.api.models.Photo;
import com.photos.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Component
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPhoto(User user, Photo photo);
}
