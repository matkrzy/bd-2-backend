package com.photos.api.controllers;

import com.photos.api.exceptions.EntityDeleteDeniedException;
import com.photos.api.exceptions.EntityNotFoundException;
import com.photos.api.models.Photo;
import com.photos.api.models.Tag;
import com.photos.api.services.PhotoService;
import com.photos.api.services.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Micha Królewski on 2018-04-29.
 * @version x
 */

@RestController
@RequestMapping("/tags")
@Api(value = "Tag resource")
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private PhotoService photoService;

    @ApiOperation(value = "Returns tags", produces = "application/json", response = Tag.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tags retrieved successfully")
    })
    @GetMapping
    public ResponseEntity getTags() {
        try {
            List<Tag> tags = tagService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Creates tag", produces = "application/json", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tag created successfully")
    })
    @PostMapping
    public ResponseEntity addTag(@RequestBody final Tag tag) {
        try {
            Tag addedTag = tagService.add(tag);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedTag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns tag by name", produces = "application/json", response = Tag.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag retrieved successfully"),
            @ApiResponse(code = 404, message = "Tag with given name doesn't exist")
    })
    @GetMapping("/{name}")
    public ResponseEntity getTagByName(@PathVariable final String name) {
        try {
            Tag tag = tagService.getByName(name);

            return ResponseEntity.status(HttpStatus.OK).body(tag);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Deletes tag if there are no photos tagged with it")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag deleted successfully"),
            @ApiResponse(code = 403, message = "No permission to delete given tag"),
            @ApiResponse(code = 404, message = "Tag with given name doesn't exist")
    })
    @DeleteMapping("/{name}")
    public ResponseEntity deleteTag(@PathVariable final String name) {
        try {
            tagService.delete(name);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityDeleteDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns photos by tag name", produces = "application/json", response = Photo.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully"),
            @ApiResponse(code = 404, message = "Tag with given name doesn't exist")
    })
    @GetMapping("/{name}/photos")
    public ResponseEntity getTagPhotos(@PathVariable final String name) {
        try {
            List<Photo> photos = photoService.getAllActiveByTag(tagService.getByName(name));

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
