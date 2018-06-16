package com.photos.api.repositories;

import com.photos.api.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Component
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByName(String name);
}
