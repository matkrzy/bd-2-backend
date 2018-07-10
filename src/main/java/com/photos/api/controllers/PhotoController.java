package com.photos.api.controllers;

import com.photos.api.exceptions.*;
import com.photos.api.models.*;
import com.photos.api.models.dtos.FetchedPhoto;
import com.photos.api.models.dtos.ShareByEmail;
import com.photos.api.models.enums.PhotoSearchCategoryMatchType;
import com.photos.api.services.PhotoService;
import com.photos.api.services.ShareService;
import com.photos.api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ShareService shareService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Returns photos ordered by creation date", produces = "application/json", response = FetchedPhoto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully")
    })
    @GetMapping
    public ResponseEntity getPhotos(
            @RequestParam(name = "categoryIds", required = false) List<Category> categories,
            @RequestParam(name = "type", required = false, defaultValue = "any") PhotoSearchCategoryMatchType categoryMatchType,
            Pageable pageable
    ) {
        try {
            List<FetchedPhoto> photos;

            if (categoryMatchType == null || categories == null) {
                photos = photoService.getAllActive(pageable);
            } else {
                if (categoryMatchType == PhotoSearchCategoryMatchType.all) {
                    photos = photoService.getAllActiveMatchingAllOfCategories(categories, pageable);
                } else {
                    photos = photoService.getAllActiveMatchingAnyOfCategories(categories, pageable);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photos sorted by likes in descending order", produces = "application/json", response = FetchedPhoto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully")
    })
    @GetMapping("/hot")
    public ResponseEntity getHotPhotos(
            Pageable pageable
    ) {
        try {
            List<FetchedPhoto> photos = photoService.getAllActiveOrderedByLikes(pageable);

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photos added in last 7 days sorted by likes in descending order", produces = "application/json", response = FetchedPhoto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully")
    })
    @GetMapping("/trending")
    public ResponseEntity getTrendingPhotos(
            @RequestParam(name = "time", required = false, defaultValue = "604800000") Long time,
            Pageable pageable
    ) {
        try {
            Date date = new Date(System.currentTimeMillis() - time);
            List<FetchedPhoto> photos = photoService.getAllActiveNewerThanOrderedByLikes(date, pageable);

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Creates photo", produces = "application/json", response = FetchedPhoto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Photo created successfully")
    })
    @PostMapping
    public ResponseEntity addPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description
    ) {
        try {
            User currentUser = userService.getCurrent();
            Photo addedPhoto = photoService.add(file, description);

            return ResponseEntity.status(HttpStatus.CREATED).body(new FetchedPhoto(addedPhoto, currentUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photo by ID", produces = "application/json", response = FetchedPhoto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photo retrieved successfully"),
            @ApiResponse(code = 403, message = "No permission to retrieve given photo"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity getPhoto(@PathVariable final Long id) {
        try {
            User currentUser = userService.getCurrent();
            Photo photo = photoService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(new FetchedPhoto(photo, currentUser));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityGetDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Updates photo", produces = "application/json", response = FetchedPhoto.class)
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
            User currentUser = userService.getCurrent();
            Photo updatedPhoto = photoService.update(photo);

            return ResponseEntity.status(HttpStatus.OK).body(new FetchedPhoto(updatedPhoto, currentUser));
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
    public ResponseEntity sharePhoto(@PathVariable final Long id, @RequestBody final ShareByEmail shareByEmail) {
        Share share;

        try {
            share = shareService.getShareFromShareByEmail(shareByEmail);
        } catch (EntityGetDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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

    @ApiOperation(value = "Likes photo by ID", produces = "application/json", response = Like.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Like given successfully"),
            @ApiResponse(code = 403, message = "No permission to like given photo"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
    @PostMapping("/{id}/like")
    public ResponseEntity likePhoto(@PathVariable final Long id) {
        try {
            Like like = photoService.like(id);

            return ResponseEntity.status(HttpStatus.CREATED).body(like);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PhotoLikeDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Unikes photo by ID", produces = "application/json", response = Like.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Like removed successfully"),
            @ApiResponse(code = 403, message = "No permission to unlike given photo"),
            @ApiResponse(code = 404, message = "Photo with given ID doesn't exist")
    })
    @DeleteMapping("/{id}/like")
    public ResponseEntity unlikePhoto(@PathVariable final Long id) {
        try {
            photoService.unlike(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PhotoUnlikeDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
