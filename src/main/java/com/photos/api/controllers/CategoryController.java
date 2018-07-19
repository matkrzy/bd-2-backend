package com.photos.api.controllers;

import com.photos.api.exceptions.*;
import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.dtos.FetchedCategory;
import com.photos.api.models.dtos.FetchedPhoto;
import com.photos.api.services.CategoryService;
import com.photos.api.services.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
@Api(value = "Category resource")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PhotoService photoService;

    @ApiOperation(value = "Returns categories", produces = "application/json", response = FetchedCategory.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categories retrieved successfully")
    })
    @GetMapping
    public ResponseEntity getCategories() {
        try {
            List<FetchedCategory> categories = categoryService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Creates category", produces = "application/json", response = FetchedCategory.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category created successfully"),
            @ApiResponse(code = 400, message = "Invalid entity given")
    })
    @PostMapping
    public ResponseEntity addCategory(@RequestBody final Category category) {
        try {
            Category addedCategory = categoryService.add(category);

            return ResponseEntity.status(HttpStatus.CREATED).body(new FetchedCategory(addedCategory));
        } catch (EntityOwnerInvalidException | EntityParentInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns category by ID", produces = "application/json", response = FetchedCategory.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category retrieved successfully"),
            @ApiResponse(code = 404, message = "Category with given ID doesn't exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity getCategory(@PathVariable final Long id) {
        try {
            Category category = categoryService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(new FetchedCategory(category));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Updates category", produces = "application/json", response = FetchedCategory.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category updated successfully"),
            @ApiResponse(code = 400, message = "Invalid entity given"),
            @ApiResponse(code = 403, message = "No permission to update given category"),
            @ApiResponse(code = 404, message = "Category with given ID doesn't exist")
    })
    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable final Long id, @RequestBody final Category category) {
        if (!id.equals(category.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(category);
        }

        try {
            Category updatedCategory = categoryService.update(category);

            return ResponseEntity.status(HttpStatus.OK).body(new FetchedCategory(updatedCategory));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityUpdateDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityOwnerChangeDeniedException | EntityParentInvalidException | EntityOwnerInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Deletes category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category deleted successfully"),
            @ApiResponse(code = 403, message = "No permission to delete given category"),
            @ApiResponse(code = 404, message = "Category with given ID doesn't exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable final Long id) {
        try {
            categoryService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityDeleteDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns category photos by category ID", produces = "application/json", response = FetchedPhoto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully"),
            @ApiResponse(code = 404, message = "Category with given ID doesn't exist")
    })
    @GetMapping("/{id}/photos")
    public ResponseEntity getCategoryPhotos(
            @PathVariable final Long id,
            Pageable pageable
    ) {
        try {
            List<FetchedPhoto> photos = photoService.getAllActiveByCategory(categoryService.getById(id), pageable);

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
