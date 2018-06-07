package com.photos.api.controllers;

import com.photos.api.models.Category;
import com.photos.api.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Micha Królewski on 2018-05-06.
 * @version x
 */

@RestController
@RequestMapping("/categories")
@Api(value = "Category resource", description = "Returns categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Returns categories", response = Category.class)
    @GetMapping
    public ResponseEntity getCategories() {
        try {
            List<Category> categories = categoryService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Creates category")
    @PostMapping
    public ResponseEntity addCategory(@RequestBody final Category category) {
        try {
            Category addedCategory = categoryService.add(category);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Returns category by ID", response = Category.class)
    @GetMapping("/{id}")
    public ResponseEntity getCategory(@PathVariable final Long id) {
        try {
            Category category = categoryService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Updates category")
    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable final Long id, @RequestBody final Category category) {
        if (!id.equals(category.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(category);
        }

        try {
            Category updatedCategory = categoryService.update(category);

            return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Removes category")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable final Long id) {
        try {
            categoryService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
