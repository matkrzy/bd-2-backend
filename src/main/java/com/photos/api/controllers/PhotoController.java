package com.photos.api.controllers;

import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.User;
import com.photos.api.services.PhotoService;
import com.photos.api.services.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version x
 */

@RestController
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "Returns photos", response = Photo.class)
    @GetMapping
    public ResponseEntity getPhotos() {
        try {
            List<Photo> photos = photoService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Creates photo")
    @PostMapping
    public ResponseEntity addPhoto(@RequestBody final Photo photo) {
        try {
            Photo addedPhoto = photoService.add(photo);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedPhoto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Returns photo by ID", response = Photo.class)
    @GetMapping("/{id}")
    public ResponseEntity getPhoto(@PathVariable final Long id) {
        try {
            Photo photo = photoService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(photo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Updates photo")
    @PutMapping("/{id}")
    public ResponseEntity updatePhoto(@PathVariable final Long id, @RequestBody final Photo photo) {
        if (!id.equals(photo.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(photo);
        }

        try {
            Photo updatedPhoto = photoService.update(photo);

            return ResponseEntity.status(HttpStatus.OK).body(updatedPhoto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Removes photo")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePhoto(@PathVariable final Long id) {
        try {
            photoService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Returns photo categories by photo ID", response = Category.class)
    @GetMapping("/{id}/categories")
    public ResponseEntity getUserCategories(@PathVariable final Long id) {
        try {
            Set<Category> categories = photoService.getById(id).getCategories();

            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
