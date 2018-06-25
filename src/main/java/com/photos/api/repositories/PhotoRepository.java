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
    List<Photo> findAllByState(PhotoState state);

    List<Photo> findAllByVisibilityAndStateOrUserAndStateOrShares_UserAndState(PhotoVisibility visibility, PhotoState state1, User user1, PhotoState state2, User user2, PhotoState state3);

    List<Photo> findAllByUserAndState(User user, PhotoState state);

    List<Photo> findAllByUserAndVisibilityAndStateOrUserAndShares_UserAndState(User user1, PhotoVisibility visibility, PhotoState state1, User user2, User user3, PhotoState state2);

    List<Photo> findAllByCategoriesAndState(Category category, PhotoState state);

    List<Photo> findAllByCategoriesAndVisibilityAndStateOrCategoriesAndUserAndStateOrCategoriesAndShares_UserAndState(Category category1, PhotoVisibility visibility, PhotoState state1, Category category2, User user1, PhotoState state2, Category category3, User user2, PhotoState state3);

    List<Photo> findAllByTagsAndState(Tag tag, PhotoState state);

    List<Photo> findAllByTagsAndVisibilityAndStateOrTagsAndUserAndStateOrTagsAndShares_UserAndState(Tag tag1, PhotoVisibility visibility, PhotoState state1, Tag tag2, User user1, PhotoState state2, Tag tag3, User user2, PhotoState state3);
}
