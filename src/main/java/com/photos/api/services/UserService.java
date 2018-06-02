package com.photos.api.services;

import com.photos.api.models.Category;
import com.photos.api.models.User;
import com.photos.api.models.enums.Role;
import com.photos.api.models.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoToCategoryRepository PTCRepository;

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getOne(final String email) {
        String sessionEmail = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        if (!email.equals(sessionEmail) && user != null) {
            User ret = new User();
            ret.setUserID(user.getUserID());
            ret.setEmail(user.getEmail());
            return ret;
        }
        return user;
    }

    public boolean addUser(final User user) throws IOException {
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 == null) {
            if (user.getEmail() == null || user.getPassword() == null || user.getLastName() == null || user.getFirstName() == null) {
                return false;
            }

            user.setRole(Role.USER.getText());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            Files.createDirectory(Paths.get(UPLOAD_ROOT + "/" + user.getEmail()));
            Category category = new Category();
            category.setName("ARCHIVES");
            category.setParentCategory(null);
            category.setUser(user);
            categoryRepository.save(category);

        } else {
            return false;
        }
        return true;
    }

    public boolean updateUser(final User user) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        User userToUpdate = userRepository.findByEmail(email);

        if (user.getFirstName() != null)
            userToUpdate.setFirstName(user.getFirstName());

        if (user.getLastName() != null)
            userToUpdate.setLastName(user.getLastName());

        if (user.getPassword() != null)
            userToUpdate.setPassword(user.getPassword());

        try {

            userRepository.save(userToUpdate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteUser() {
        User user = userRepository.findByEmail(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        try {

            List<Category> categories = categoryRepository.findAllByUser(user);
            for (Category category : categories) {
                PTCRepository.deleteAllByCategory(category);
            }
            categoryRepository.deleteAllByUser(user);

            shareRepository.deleteAllByUser(user);
            tagRepository.deleteAllByUser(user);
            photoRepository.deleteAllByOwner(user);

            // TODO: 2018-05-19 delete folder with images
            userRepository.delete(user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
