package com.photos.api.services;

import com.photos.api.models.Category;
import com.photos.api.models.User;
import com.photos.api.models.repositories.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Pobiera wszyskie dzieci podanej kategorii
     *
     * @param parentid
     * @return
     */
    public List<Category> getAll(final Long parentid) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        Category parent = categoryRepository.findByCategoryIDAndUser(parentid, user);
        List<Category> categories = categoryRepository.findAllByParentCategoryAndUser(parent, user);
        return categories.size() == 0 ? null : categories;
    }

    /**
     * Dodaje kategorie do bazy
     *
     * @param category
     * @return
     */
    public boolean addCategory(Category category) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        if (categoryRepository.findByNameAndParentCategoryAndUser(category.getName(), category.getParentCategory(), user) != null) {
            return false;
        }

        if (category.getParentCategory() != null) {
            Category parentCat = categoryRepository.findByCategoryIDAndUser(category.getParentCategory().getCategoryID(), user);
            if (parentCat == null) {
                return false;
            }
            category.setParentCategory(parentCat);
        } else {
            category.setParentCategory(null);
        }

        category.setUser(user);
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            return false;
        }

        return true;

    }

    public boolean editCategory(Long id, Category category) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);

        Category categoryToUpdate = categoryRepository.findByCategoryIDAndUser(id, user);
        if (category == null) {
            return false;
        }

        try {
            if (categoryToUpdate.getParentCategory() != (category.getParentCategory())) {
                if (category.getParentCategory() == null) {
                    categoryToUpdate.setParentCategory(null);
                } else {
                    Category parentCat = categoryRepository.findByCategoryIDAndUser(category.getParentCategory().getCategoryID(), user);
                    if (parentCat == null && category.getParentCategory() != null) {
                        return false;
                    }
                    categoryToUpdate.setParentCategory(parentCat);
                }
            }
            if (!categoryToUpdate.getName().equals(category.getName()) && category.getName() != null) {
                categoryToUpdate.setName(category.getName());
            }

            categoryRepository.save(categoryToUpdate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Usuwa dana kategorie i wszystkich jej potomkow
     *
     * @param id
     * @return
     */
    public boolean deleteCategory(Long id) {
        User user = userRepository.findByEmail(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        if (user == null) {
            return false;
        }

        Category category = categoryRepository.findByCategoryIDAndUser(id, user);
        if (category == null) {
            return false;
        }
        List<Category> kids = categoryRepository.findAllByParentCategoryAndUser(category, user);
        if (kids.size() > 0) {
            deleteKids(kids, user);
        }

        try {
            categoryRepository.delete(category);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    /**
     * Rekurencyjnie usuwa potomkow kategorii
     *
     * @param kids
     * @param user
     */
    private void deleteKids(List<Category> kids, User user) {
        List<Category> kids1;
        for (Category tmpCat : kids) {
            kids1 = categoryRepository.findAllByParentCategoryAndUser(tmpCat, user);
            if (kids1 != null) {
                deleteKids(kids1, user);
            }
            categoryRepository.delete(tmpCat);
        }
    }

}
