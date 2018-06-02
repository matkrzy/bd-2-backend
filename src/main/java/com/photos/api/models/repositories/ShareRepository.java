package com.photos.api.models.repositories;

import com.photos.api.models.Photo;
import com.photos.api.models.Share;
import com.photos.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Component
public interface ShareRepository extends JpaRepository<Share, Long> {
    List<Share> findAllByUser(User user);

    Share findByPhotoAndUser(Photo photo, User user);

    void deleteAllByUser(User user);

    void deleteAllByPhoto(Photo photo);

    Share findByPhotoAndUserAndOwner(Photo photo, User user, User owner);
}
