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
@Api(value = "Category resource", description = "Returns user's categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Removes category")
    public ResponseEntity deleteCategory(@PathVariable final Long id) {
        return categoryService.deleteCategory(id) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{parent_id}")
    @ApiOperation(value = "Returns children of the category", response = Category.class)
    public ResponseEntity getCategories(@PathVariable() final Long parent_id) {
        List<Category> categories = categoryService.getAll(parent_id);
        return categories != null ?
                ResponseEntity.status(HttpStatus.OK).body(categories) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @ApiOperation(value = "Returns root categories", response = Category.class)
    public ResponseEntity getCategories() {
        List<Category> categories = categoryService.getAll(null);
        return categories != null ?
                ResponseEntity.status(HttpStatus.OK).body(categories) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    @ApiOperation(value = "Creates new category")
    public ResponseEntity addCategory(@RequestBody final Category category) {
        return categoryService.addCategory(category) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates existing category", response = Category.class)
    public ResponseEntity editCategory(@PathVariable final Long id, @RequestBody final Category category) {
        return categoryService.editCategory(id, category) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
