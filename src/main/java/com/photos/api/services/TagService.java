package com.photos.api.services;

import com.photos.api.exceptions.EntityDeleteDeniedException;
import com.photos.api.exceptions.EntityNotFoundException;
import com.photos.api.models.Tag;
import com.photos.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public List<Tag> getAllStartingWith(String q) {
        return tagRepository.findAllByNameStartingWith(q);
    }

    public Tag getByName(final String name) throws EntityNotFoundException {
        Optional<Tag> tag = tagRepository.findByName(name);

        if (!tag.isPresent()) {
            throw new EntityNotFoundException();
        }

        return tag.get();
    }

    public Tag add(final Tag tag) {
        return tagRepository.save(tag);
    }

    public void delete(final String name) throws EntityNotFoundException, EntityDeleteDeniedException {
        Tag tag = this.getByName(name);

        if (tag.getPhotos().size() > 0) {
            throw new EntityDeleteDeniedException();
        }

        tagRepository.delete(tag);
    }
}
