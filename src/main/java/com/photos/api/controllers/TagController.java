package com.photos.api.controllers;

import com.photos.api.models.Tag;
import com.photos.api.services.TagService;
import io.swagger.annotations.ApiOperation;
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
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "Returns public tags",response = Tag.class)
    @GetMapping("/public")
    public ResponseEntity getPublicTags() {
        List<Tag> tags = tagService.getPublicTags();
        return tags != null ?
                ResponseEntity.status(HttpStatus.OK).body(tags) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Returns public tags started with",response = Tag.class)
    @GetMapping("/public/{name}")
    public ResponseEntity getPublicTags(@PathVariable final String name) {
        List<Tag> tags = tagService.getPublicTags(name);
        return tags != null ?
                ResponseEntity.status(HttpStatus.OK).body(tags) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Returns private tags",response = Tag.class)
    @GetMapping
    public ResponseEntity getTags() {
        List<Tag> tags = tagService.getTags();
        return tags != null ?
                ResponseEntity.status(HttpStatus.OK).body(tags) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Returns private tags started with",response = Tag.class)
    @GetMapping("/{name}")
    public ResponseEntity getTags(@PathVariable final String name) {
        List<Tag> tags = tagService.getTags(name);
        return tags != null ?
                ResponseEntity.status(HttpStatus.OK).body(tags) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Creates new tags")
    @PostMapping
    public ResponseEntity addTags(@RequestBody final List<Tag> tags) {
        return tagService.addTags(tags) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @ApiOperation(value = "Crates new tag")
    @PostMapping("/")
    public ResponseEntity addTag(@RequestBody final Tag tag) {
        return tagService.addTag(tag) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @ApiOperation(value = "Removes tag")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTag(@PathVariable final Long id){
        return tagService.deleteTag(id) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Removes all tag")
    @DeleteMapping("/all/{id}")
    public ResponseEntity deleteTags(@PathVariable final Long id){
        return tagService.deleteTags(id) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
