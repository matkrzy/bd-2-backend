package com.photos.api.services;

import com.photos.api.models.Tag;
import com.photos.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
}
