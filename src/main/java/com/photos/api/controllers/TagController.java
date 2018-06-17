package com.photos.api.controllers;

import com.photos.api.exceptions.EntityNotFoundException;
import com.photos.api.models.Photo;
import com.photos.api.models.Tag;
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
import java.util.Set;

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

    @ApiOperation(value = "Returns tags", response = Tag.class, responseContainer = "List")
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

    @ApiOperation(value = "Creates tag", response = Tag.class)
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

    @ApiOperation(value = "Returns tag by name", response = Tag.class)
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

    @ApiOperation(value = "Returns photos by tag name", response = Photo.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully"),
            @ApiResponse(code = 404, message = "Tag with given name doesn't exist")
    })
    @GetMapping("/{name}/photos")
    public ResponseEntity getTagPhotos(@PathVariable final String name) {
        try {
            Set<Photo> photos = tagService.getByName(name).getPhotos();

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
