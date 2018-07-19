package com.photos.api.services;

import com.photos.api.exceptions.*;
import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.User;
import com.photos.api.models.dtos.FetchedCategory;
import com.photos.api.models.enums.UserRole;
import com.photos.api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<FetchedCategory> getAll() {
        return categoryRepository.findAll().stream().map(FetchedCategory::new).collect(Collectors.toList());
    }

    public List<FetchedCategory> getAllByPhoto(Photo photo) {
        return categoryRepository.findAllByPhotos(photo).stream().map(FetchedCategory::new).collect(Collectors.toList());
    }

    public List<FetchedCategory> getAllByUser(User user) {
        return categoryRepository.findAllByUser(user).stream().map(FetchedCategory::new).collect(Collectors.toList());
    }

    public Category getById(final Long id) throws EntityNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent()) {
            throw new EntityNotFoundException();
        }

        return categoryOptional.get();
    }

    public Category add(final Category category) throws EntityOwnerInvalidException, EntityParentInvalidException {
        if (category.getUser() != userService.getCurrent()) {
            throw new EntityOwnerInvalidException();
        }

        if (category.getParent() != null && category.getParent().getUser() != userService.getCurrent()) {
            throw new EntityParentInvalidException();
        }

        for (Photo photo : category.getPhotos()) {
            if (photo.getUser() != userService.getCurrent()) {
                throw new EntityOwnerInvalidException();
            }
        }

        return categoryRepository.save(category);
    }

    public Category update(final Category category) throws EntityNotFoundException, EntityUpdateDeniedException, EntityOwnerChangeDeniedException, EntityParentInvalidException, EntityOwnerInvalidException {
        Category currentCategory = this.getById(category.getId());

        if (currentCategory.getUser() != userService.getCurrent() && userService.getCurrent().getRole() != UserRole.ADMIN) {
            throw new EntityUpdateDeniedException();
        }

        if (currentCategory.getUser() != category.getUser() && userService.getCurrent().getRole() != UserRole.ADMIN) {
            throw new EntityOwnerChangeDeniedException();
        }

        if (category.getParent() != null && category.getParent().getUser() != userService.getCurrent()) {
            throw new EntityParentInvalidException();
        }

        for (Photo photo : category.getPhotos()) {
            if (photo.getUser() != userService.getCurrent()) {
                throw new EntityOwnerInvalidException();
            }
        }

        return categoryRepository.save(category);
    }

    public void delete(final Long id) throws EntityNotFoundException, EntityDeleteDeniedException {
        Category category = this.getById(id);

        if (category.getUser() != userService.getCurrent() && userService.getCurrent().getRole() != UserRole.ADMIN) {
            throw new EntityDeleteDeniedException();
        }

        categoryRepository.deleteById(id);
    }
}
