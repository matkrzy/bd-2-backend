package com.photos.api.repositories;

import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.Tag;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.PhotoVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@Component
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByCategories(Category category);

    List<Photo> findAllByCategoriesAndVisibilityOrCategoriesAndUserOrCategoriesAndShares_User(Category category1, PhotoVisibility visibility, Category category2, User user1, Category category3, User user2);

    List<Photo> findAllByTags(Tag tag);

    List<Photo> findAllByTagsAndVisibilityOrTagsAndUserOrTagsAndShares_User(Tag tag1, PhotoVisibility visibility, Tag tag2, User user1, Tag tag3, User user2);

    List<Photo> findAllByUser(User user);

    List<Photo> findAllByUserAndVisibilityOrUserAndShares_User(User user1, PhotoVisibility visibility, User user2, User user3);

    List<Photo> findAllByUserOrVisibilityOrShares_User(User user1, PhotoVisibility visibility, User user2);
}
