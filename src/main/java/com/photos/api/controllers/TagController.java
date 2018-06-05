package com.photos.api.controllers;

import com.photos.api.models.Tag;
import com.photos.api.services.PhotoService;
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

    @ApiOperation(value = "Returns tags", response = Tag.class)
    @GetMapping
    public ResponseEntity getTags() {
        try {
            List<Tag> tags = tagService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
