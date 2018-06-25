package com.photos.api.controllers;

import com.photos.api.exceptions.*;
import com.photos.api.models.*;
import com.photos.api.services.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Photo resource")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @ApiOperation(value = "Returns photos", produces = "application/json", response = Photo.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully")
    })
    @GetMapping
    public ResponseEntity getPhotos() {
        try {
            List<Photo> photos = photoService.getAllActive();

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Creates photo", produces = "application/json", response = Photo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Photo created successfully")
    })
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

    @ApiOperation(value = "Returns photo by ID", produces = "application/json", response = Photo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photo retrieved successfully"),
            @ApiResponse(code = 403, message = "No permission to retrieve given photo"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
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

    @ApiOperation(value = "Updates photo", produces = "application/json", response = Photo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photo updated successfully"),
            @ApiResponse(code = 400, message = "Invalid entity given"),
            @ApiResponse(code = 403, message = "No permission to update given photo"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
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
        } catch (EntityOwnerChangeDeniedException | EntityOwnerInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Deletes photo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photo deleted successfully"),
            @ApiResponse(code = 403, message = "No permission to delete given photo"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
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

    @ApiOperation(value = "Returns photo categories by photo ID", produces = "application/json", response = Category.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categories retrieved successfully"),
            @ApiResponse(code = 403, message = "No permission to retrieve given photo categories"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
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

    @ApiOperation(value = "Returns photo shares by photo ID", produces = "application/json", response = Share.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shares retrieved successfully"),
            @ApiResponse(code = 403, message = "No permission to retrieve given photo shares"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
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

    @ApiOperation(value = "Shares photo by photo ID", produces = "application/json", response = Share.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photo shared successfully"),
            @ApiResponse(code = 400, message = "Invalid entity given"),
            @ApiResponse(code = 403, message = "No permission to share given photo"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
    @PostMapping("/{id}/shares")
    public ResponseEntity sharePhoto(@PathVariable final Long id, @RequestBody final Share share) {
        if (!id.equals(share.getPhoto().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(share);
        }

        try {
            Share addedShare = photoService.share(id, share);

            return ResponseEntity.status(HttpStatus.OK).body(addedShare);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PhotoShareDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (PhotoShareTargetInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photo tags by photo ID", produces = "application/json", response = Tag.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tags retrieved successfully"),
            @ApiResponse(code = 403, message = "No permission to retrieve given photo tags"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
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

    @ApiOperation(value = "Returns photo likes by photo ID", produces = "application/json", response = Like.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Likes retrieved successfully"),
            @ApiResponse(code = 403, message = "No permission to retrieve given photo likes"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
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
