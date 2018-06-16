package com.photos.api.services;

import com.photos.api.exceptions.EntityDeleteDeniedException;
import com.photos.api.exceptions.EntityNotFoundException;
import com.photos.api.exceptions.EntityUpdateDeniedException;
import com.photos.api.models.Category;
import com.photos.api.models.User;
import com.photos.api.models.enums.UserRole;
import com.photos.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Micha Królewski on 2018-04-14.
 * @version 1.0
 */

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(final Long id) throws EntityNotFoundException {
        //TODO: Walidacja czy możemy pobrać wrażliwe dane tego usera
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new EntityNotFoundException();
        }

        return user.get();
    }

    public User getByEmail(final String email) throws EntityNotFoundException {
        //TODO: Walidacja czy możemy pobrać wrażliwe dane tego usera
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new EntityNotFoundException();
        }

        return user.get();
    }

    public User getCurrent() {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new IllegalArgumentException("Current user not found.");
        }

        return user.get();
    }

    public User add(final User user) {
        //TODO: Walidacja przesłanych danych
        user.setRole(UserRole.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        Category category = new Category();
        category.setName("ARCHIVES");
        category.setParent(null);
        category.setUser(savedUser);
        categoryRepository.save(category);

        return savedUser;
    }

    public User update(final User user) throws EntityNotFoundException, EntityUpdateDeniedException {
        User currentUser = this.getById(user.getId());

        if (currentUser != this.getCurrent()) {
            throw new EntityUpdateDeniedException();
        }

        //TODO: Walidacja przesłanych danych

        return userRepository.save(user);
    }

    public void delete(final Long id) throws EntityNotFoundException, EntityDeleteDeniedException {
        User user = this.getById(id);

        if (user != this.getCurrent()) {
            throw new EntityDeleteDeniedException();
        }

        userRepository.deleteById(id);
    }
}
