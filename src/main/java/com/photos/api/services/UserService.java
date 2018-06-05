package com.photos.api.services;

import com.photos.api.models.Category;
import com.photos.api.models.User;
import com.photos.api.models.enums.UserRole;
import com.photos.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.photos.api.services.ImageService.UPLOAD_ROOT;

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

    public User getById(final Long id) {
        //TODO: Walidacja czy możemy pobrać wrażliwe dane tego usera
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new IllegalArgumentException(String.format("User with ID %d not found.", id));
        }

        return user.get();
    }

    public User getByEmail(final String email) {
        //TODO: Walidacja czy możemy pobrać wrażliwe dane tego usera
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new IllegalArgumentException(String.format("User with e-mail address %s not found.", email));
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

    public User add(final User user) throws IOException {
        //TODO: Walidacja przesłanych danych
        user.setRole(UserRole.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        Files.createDirectory(Paths.get(UPLOAD_ROOT + "/" + savedUser.getEmail()));

        Category category = new Category();
        category.setName("ARCHIVES");
        category.setParent(null);
        category.setUser(savedUser);
        categoryRepository.save(category);

        return savedUser;
    }

    public User update(final User user) {
        //TODO: Walidacja czy możemy aktualizować tego usera
        return userRepository.save(user);
    }

    public void delete(final Long id) {
        //TODO: Walidacja czy możemy usunąć tego usera
        userRepository.deleteById(id);
    }
}
