package com.photos.api.services;

import com.photos.api.models.*;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.ShareState;
import com.photos.api.models.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PhotoToCategoryRepository ptcRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Photo> getAll() {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        List<Photo> photos = photoRepository.findAllByOwnerAndPhotoState(user, PhotoState.ACTIVE);
        return photos;
    }

    public List<Photo> getByCategoryAny(List<Category> categories) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);


        List<PhotoToCategory> ptcs = ptcRepository.findAllByCategoryIn(categories);

        Map<Long, Photo> list = new HashMap<>();
        List<Photo> photos = new ArrayList<>();
        for (PhotoToCategory ptc : ptcs) {
            list.put(ptc.getPhoto().getPhotoID(), ptc.getPhoto());
        }
        for (Map.Entry<Long, Photo> entry : list.entrySet()) {
            photos.add(entry.getValue());
        }
        return photos;
    }

    public List<Photo> getByCategoryAll(List<Category> categories) {

        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        List<Photo> photos = new ArrayList<>();
        List<PhotoToCategory> ptcs = ptcRepository.findAllByCategoryIn(categories);
        ptcs.sort((o1, o2) -> o1.getPhoto().getPhotoID().compareTo(o2.getPhoto().getPhotoID()));

        int counter = 0;
        long lastId = 0;
        for (PhotoToCategory ptc : ptcs) {
            if (lastId == ptc.getPhoto().getPhotoID()) {
                counter++;
            } else {
                lastId = ptc.getPhoto().getPhotoID();
                counter = 1;
            }
            if (counter == categories.size()) {
                photos.add(ptc.getPhoto());
            }
        }
        return photos;
    }

    public List<Photo> getPublic() {
        return photoRepository.findAllByShareStateAndPhotoState(ShareState.PUBLIC, PhotoState.ACTIVE);
    }

    public List<Photo> getTrending() {
        System.out.println(new Timestamp(System.currentTimeMillis() - 259_200_000));
        return photoRepository.findAllByShareStateAndPhotoStateAndUploadTimeGreaterThan(ShareState.PUBLIC, PhotoState.ACTIVE, new Timestamp(System.currentTimeMillis() - 259200));
    }

    private List<Tag> getTagObjects(List<Tag> tags) {
        List<Tag> ret = new ArrayList<>();
        for (Tag tag : tags) {
            List<Tag> tmp = tagRepository.findAllByName(tag.getName());
            ret.addAll(tmp);
        }
        ret.sort((o1, o2) -> o1.getPhoto().getPhotoID().compareTo(o2.getPhoto().getPhotoID()));

        return ret;
    }

    public List<Photo> getByTagsAll(List<Tag> tagss, ShareState shareState) {

        List<Tag> tags = getTagObjects(tagss);

        List<Photo> photos = new ArrayList<>();
        int counter = 0;
        long lastId = 0;

        for (Tag tag : tags) {
            if (lastId == tag.getPhoto().getPhotoID()) {
                counter++;
            } else {
                lastId = tag.getPhoto().getPhotoID();
                counter = 1;
            }
            if (counter == tagss.size()) {
                if (tag.getPhoto().getShareState() == shareState && tag.getPhoto().getPhotoState() == PhotoState.ACTIVE) {
                    photos.add(tag.getPhoto());
                }
            }
        }
        return photos;
    }

    public List<Photo> getByTagsAny(List<Tag> tagss, ShareState shareState) {

        List<Tag> tags = getTagObjects(tagss);

        Map<Long, Photo> list = new HashMap<>();
        List<Photo> photos = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getPhoto().getShareState() == shareState && tag.getPhoto().getPhotoState() == PhotoState.ACTIVE) {
                list.put(tag.getPhoto().getPhotoID(), tag.getPhoto());
            }
        }
        for (Map.Entry<Long, Photo> entry : list.entrySet()) {
            photos.add(entry.getValue());
        }
        return photos;
    }

    public List<Photo> getShared() {
        User user = userRepository.findByEmail(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        List<Photo> photos = new ArrayList<>();
        List<Share> shares = shareRepository.findAllByUser(user);

        for (Share share : shares) {
            photos.add(share.getPhoto());
        }
        return photos;
    }

    public Photo getPhoto(final Long id) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Photo photo = photoRepository.findByPhotoIDAndPhotoState(id, PhotoState.ACTIVE);

        return photo != null && photo.getOwner().getEmail().equals(email) ? photo : null;
    }

    public List<Photo> getPhoto(final String name) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        List<Photo> photos = photoRepository.findAllByNameAndPhotoStateAndOwner(name, PhotoState.ACTIVE, user);

        return photos.size() > 0 ? photos : null;
    }

    public Long addPhoto(final Photo photo) {
        Long id;
        if (photo.getName() == null) {
            return -1L;
        }
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        try {

            User user = userRepository.findByEmail(email);
            photo.setOwner(user);
            photo.setUploadTime(new Timestamp(System.currentTimeMillis()));
            if (photo.getPhotoState() == null) {
                photo.setPhotoState(PhotoState.ACTIVE);
            }
            if (photo.getShareState() == null) {
                photo.setShareState(ShareState.PRIVATE);
            }
            if (photo.getDescription() == null) {
                photo.setDescription("");
            }

            photoRepository.save(photo);
            id = photo.getPhotoID();
        } catch (Exception e) {
            return -1L;
        }
        return id;

    }

    public boolean deletePhoto(Long id) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        Photo check = photoRepository.findByPhotoIDAndOwner(id, user);

        if (check == null) {
            return false;
        }

        try {
            ptcRepository.deleteAllByPhoto(check);
            shareRepository.deleteAllByPhoto(check);
            rateRepository.deleteAllByPhoto(check);
            tagRepository.deleteAllByPhoto(check);
            Files.deleteIfExists(Paths.get(UPLOAD_ROOT + "\\" + email + "\\", check.getName()));
            photoRepository.delete(check);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean editPhoto(Long id, Photo photo) {

        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        Photo photoToUpdate = photoRepository.findByPhotoIDAndOwner(id, user);

        if (photoToUpdate == null) {
            return false;
        }

        try {

            if (!photoToUpdate.getDescription().equals(photo.getDescription()) && photo.getDescription() != null) {
                photoToUpdate.setDescription(photo.getDescription());
            }
            if (!photoToUpdate.getPhotoState().equals(photo.getPhotoState()) && photo.getPhotoState() != null) {
                photoToUpdate.setPhotoState(photo.getPhotoState());
            }
            if (!photoToUpdate.getShareState().equals(photo.getShareState()) && photo.getShareState() != null) {
                photoToUpdate.setShareState(photo.getShareState());
            }

            photoRepository.save(photoToUpdate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public int getPhotosCount(ShareState ss, PhotoState ps) {

        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        return ss == ShareState.PRIVATE ?
                photoRepository.countAllByOwnerAndPhotoState(user, ps) :
                photoRepository.countAllByShareStateAndPhotoState(ss, ps);

    }


    public List<Photo> getArchived() {

        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        return photoRepository.findAllByShareStateAndPhotoStateAndOwner(ShareState.PRIVATE, PhotoState.ARCHIVED, user);
    }

    public List<Photo> getNoCategoryPhotos() {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        return photoRepository.findAllByShareStateAndPhotoStateAndHasCategoryAndOwner(ShareState.PRIVATE, PhotoState.ACTIVE, false, user);
    }
}
