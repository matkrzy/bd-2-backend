package com.photos.api.services;

import com.photos.api.models.Category;
import com.photos.api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version 1.0
 */

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(final Long id) {
        //TODO: Walidacja czy możemy pobrać tę kategorię
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            throw new IllegalArgumentException(String.format("Category with ID %d not found.", id));
        }

        return category.get();
    }

    public Category add(final Category category) {
        //TODO: Walidacja przesłanych danych
        return categoryRepository.save(category);
    }

    public Category update(final Category category) {
        //TODO: Walidacja czy możemy aktualizować tę kategorię
        return categoryRepository.save(category);
    }

    public void delete(final Long id) {
        //TODO: Walidacja czy możemy usunąć tę kategorię
        categoryRepository.deleteById(id);
    }
}
