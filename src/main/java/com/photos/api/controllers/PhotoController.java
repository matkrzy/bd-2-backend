package com.photos.api.controllers;

import com.photos.api.exceptions.*;
import com.photos.api.models.*;
import com.photos.api.services.PhotoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @ApiOperation(value = "Returns photos", response = Photo.class)
    @GetMapping
    public ResponseEntity getPhotos() {
        try {
            List<Photo> photos = photoService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Creates photo")
    @PostMapping
    public ResponseEntity addPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description
    ) {
        try {
            Photo addedPhoto = photoService.add(file, description);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedPhoto);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photo by ID", response = Photo.class)
    @GetMapping("/{id}")
    public ResponseEntity getPhoto(@PathVariable final Long id) {
        try {
            Photo photo = photoService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(photo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityGetDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityUpdateDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityOwnerChangeDeniedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Removes photo")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePhoto(@PathVariable final Long id) {
        try {
            photoService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityDeleteDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photo categories by photo ID", response = Category.class)
    @GetMapping("/{id}/categories")
    public ResponseEntity getPhotoCategories(@PathVariable final Long id) {
        try {
            Set<Category> categories = photoService.getById(id).getCategories();

            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityGetDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photo shares by photo ID", response = Share.class)
    @GetMapping("/{id}/shares")
    public ResponseEntity getPhotoShares(@PathVariable final Long id) {
        try {
            Set<Share> shares = photoService.getById(id).getShares();

            return ResponseEntity.status(HttpStatus.OK).body(shares);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityGetDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photo tags by photo ID", response = Tag.class)
    @GetMapping("/{id}/tags")
    public ResponseEntity getPhotoTags(@PathVariable final Long id) {
        try {
            Set<Tag> tags = photoService.getById(id).getTags();

            return ResponseEntity.status(HttpStatus.OK).body(tags);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityGetDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photo likes by photo ID", response = Like.class)
    @GetMapping("/{id}/likes")
    public ResponseEntity getPhotoLikes(@PathVariable final Long id) {
        try {
            Set<Like> likes = photoService.getById(id).getLikes();

            return ResponseEntity.status(HttpStatus.OK).body(likes);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityGetDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
