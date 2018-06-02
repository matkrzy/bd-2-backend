package com.photos.api.services;

import com.photos.api.models.Photo;
import com.photos.api.models.Tag;
import com.photos.api.models.User;
import com.photos.api.models.repositories.PhotoRepository;
import com.photos.api.models.repositories.TagRepository;
import com.photos.api.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Zwraca wszystkie tagi z bazy
     *
     * @return {lista nazw tagow}
     */
    public List<Tag> getPublicTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags;
    }

    /**
     * Zwraca wszystkie tagi zaczynajace sie od podanego wzorca
     *
     * @param name
     * @return
     */
    public List<Tag> getPublicTags(String name) {
        List<Tag> tags = tagRepository.findAllByNameLike(name + "%");
        return tags;
    }

    /**
     * Zwraca wszystkie tagu uzytkownika
     *
     * @return
     */
    public List<Tag> getTags() {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        List<Tag> tags = tagRepository.findAllByUser(user);
        return tags;
    }

    /**
     * Zwraca wszystkie tagu uzytkownika rozpoczynajace sie od podanego wzorca
     *
     * @param name
     * @return
     */
    public List<Tag> getTags(String name) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        List<Tag> tags = tagRepository.findAllByNameLikeAndUser(name + "%", user);
        return tags;
    }

    /**
     * Pobiera wszystkie tagi dla danego zdjecia
     *
     * @param photo
     * @return {lista nazw tagow}
     */
    public List<Tag> getPhotoTags(final Photo photo) {
        List<Tag> tags = tagRepository.findAllByPhoto(photo);
        return tags;
    }

    /**
     * Sprawdza czy istnieje zdjecie, do ktorego ma zostac dodany tag
     * Jezeli tak zapisuje tag do bazy
     *
     * @param tags
     * @return
     */
    public boolean addTags(List<Tag> tags) {
        for (Tag tag : tags) {
            addTag(tag);
        }
        return true;
    }

    public boolean addTag(Tag tag) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        if (tagRepository.findByPhotoAndName(tag.getPhoto(), tag.getName()) != null) {
            return false;
        }
        if (photoRepository.findByPhotoID(tag.getPhoto().getPhotoID()) == null) {
            return false;
        }

        try {
            tag.setUser(user);
            tagRepository.save(tag);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteTag(Long id) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        Tag tag = tagRepository.findByTagIDAndUser(id, user);
        if (tag == null) {
            return false;
        }

        try {
            tagRepository.delete(tag);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
