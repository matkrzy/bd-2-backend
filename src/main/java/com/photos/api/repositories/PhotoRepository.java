package com.photos.api.repositories;

import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.Tag;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.PhotoVisibility;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@Component
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByState(PhotoState state, Pageable pageable);

    List<Photo> findAllByStateAndCreationDateGreaterThan(PhotoState state, Date date, Pageable pageable);

    List<Photo> findAllByVisibilityAndStateOrUserAndStateOrShares_UserAndState(PhotoVisibility visibility, PhotoState state1, User user1, PhotoState state2, User user2, PhotoState state3, Pageable pageable);

    List<Photo> findAllByVisibilityAndStateAndCreationDateGreaterThanOrUserAndStateAndCreationDateGreaterThanOrShares_UserAndStateAndCreationDateGreaterThan(PhotoVisibility visibility, PhotoState state1, Date date1, User user1, PhotoState state2, Date date2, User user2, PhotoState state3, Date date3, Pageable pageable);

    List<Photo> findAllByUserAndState(User user, PhotoState state, Pageable pageable);

    List<Photo> findAllByUserAndVisibilityAndStateOrUserAndShares_UserAndState(User user1, PhotoVisibility visibility, PhotoState state1, User user2, User user3, PhotoState state2, Pageable pageable);

    List<Photo> findAllByCategoriesAndState(Category category, PhotoState state, Pageable pageable);

    Set<Photo> findDistinctByCategoriesInAndState(List<Category> categories, PhotoState state, Pageable pageable);

    List<Photo> findAllByCategoriesAndVisibilityAndStateOrCategoriesAndUserAndStateOrCategoriesAndShares_UserAndState(Category category1, PhotoVisibility visibility, PhotoState state1, Category category2, User user1, PhotoState state2, Category category3, User user2, PhotoState state3, Pageable pageable);

    Set<Photo> findDistinctByCategoriesInAndVisibilityAndStateOrCategoriesInAndUserAndStateOrCategoriesInAndShares_UserAndState(List<Category> categories1, PhotoVisibility visibility, PhotoState state1, List<Category> categories2, User user1, PhotoState state2, List<Category> categories3, User user2, PhotoState state3, Pageable pageable);

    List<Photo> findAllByTagsAndState(Tag tag, PhotoState state, Pageable pageable);

    List<Photo> findAllByTagsAndVisibilityAndStateOrTagsAndUserAndStateOrTagsAndShares_UserAndState(Tag tag1, PhotoVisibility visibility, PhotoState state1, Tag tag2, User user1, PhotoState state2, Tag tag3, User user2, PhotoState state3, Pageable pageable);
}
