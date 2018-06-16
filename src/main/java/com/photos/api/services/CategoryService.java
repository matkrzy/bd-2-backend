package com.photos.api.services;

import com.photos.api.exceptions.*;
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

    @Autowired
    private UserService userService;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(final Long id) throws EntityNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent()) {
            throw new EntityNotFoundException();
        }

        return categoryOptional.get();
    }

    public Category add(final Category category) throws EntityOwnerInvalidException {
        if (category.getUser() != userService.getCurrent()) {
            throw new EntityOwnerInvalidException();
        }

        //TODO: Walidacja przesłanych danych
        // - czy mamy prawo dostępu do użycia podanej kategorii nadrzędnej
        // - czy mamy prawo do przypisania danych zdjęć

        return categoryRepository.save(category);
    }

    public Category update(final Category category) throws EntityNotFoundException, EntityUpdateDeniedException, EntityOwnerChangeDeniedException {
        Category currentCategory = this.getById(category.getId());

        if (currentCategory.getUser() != userService.getCurrent()) {
            throw new EntityUpdateDeniedException();
        }

        if (currentCategory.getUser() != category.getUser()) {
            throw new EntityOwnerChangeDeniedException();
        }

        //TODO: Walidacja przesłanych danych

        return categoryRepository.save(category);
    }

    public void delete(final Long id) throws EntityNotFoundException, EntityDeleteDeniedException {
        Category category = this.getById(id);

        if (category.getUser() != userService.getCurrent()) {
            throw new EntityDeleteDeniedException();
        }

        categoryRepository.deleteById(id);
    }
}
