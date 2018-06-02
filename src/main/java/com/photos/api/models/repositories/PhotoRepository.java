package com.photos.api.models.repositories;

import com.photos.api.models.Photo;
import com.photos.api.models.User;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.ShareState;
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

    List<Photo> findAllByOwnerAndPhotoState(User user, PhotoState photoState);

    List<Photo> findAllByShareStateAndPhotoState(ShareState ss, PhotoState photoState);

    List<Photo> findAllByShareStateAndPhotoStateAndUploadTimeGreaterThan(ShareState ss, PhotoState photoState, Timestamp ts);

    List<Photo> findAllByNameAndPhotoStateAndOwner(String name, PhotoState photoState, User owner);

    int countAllByShareStateAndPhotoState(ShareState ss, PhotoState photoState);

    int countAllByOwnerAndPhotoState(User user, PhotoState photoState);

    Photo findByPhotoIDAndPhotoState(Long id, PhotoState photoState);

    Photo findByPhotoIDAndPhotoStateAndShareState(Long id, PhotoState photoState, ShareState shareState);

    Photo findByPhotoIDAndOwnerAndPhotoState(Long id, User owner, PhotoState photoState);

    void deleteAllByOwner(User owner);

    Photo findByPhotoIDAndOwner(Long id, User user);

    Photo findByPhotoIDAndPhotoStateAndShareStateAndOwner(Long id, PhotoState active, ShareState aPrivate, User owner);

    Photo findByPhotoID(Long photoID);

    List<Photo> findAllByShareStateAndPhotoStateAndOwner(ShareState aPrivate, PhotoState archived, User user);

    List<Photo> findAllByShareStateAndPhotoStateAndHasCategoryAndOwner(ShareState aPrivate, PhotoState archived, boolean has, User user);
}
