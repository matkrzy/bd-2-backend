package com.photos.api.models.repositories;

import com.photos.api.models.Photo;
import com.photos.api.models.Tag;
import com.photos.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Component
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByPhoto(Photo photo);

    List<Tag> findAllByNameLike(String name);

    List<Tag> findAllByUser(User user);

    List<Tag> findAllByNameLikeAndUser(String name, User user);

    List<Tag> findAllByName(String name);

    Tag findByTagIDAndUser(Long id, User user);

    void deleteAllByUser(User userID);

    void deleteAllByPhoto(Photo photo);

    Tag findByPhotoAndName(Photo photo, String name);
}
