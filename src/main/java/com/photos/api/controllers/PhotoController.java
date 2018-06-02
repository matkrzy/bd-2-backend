package com.photos.api.controllers;

import com.photos.api.models.Photo;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.ShareState;
import com.photos.api.services.PhotoService;
import com.photos.api.services.PhotoToCategoryService;
import com.photos.api.services.RateService;
import com.photos.api.services.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    private PhotoToCategoryService PTCService;

    @Autowired
    private RateService rateService;

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "Creates new photo")
    @PostMapping
    public ResponseEntity addPhoto(@RequestBody final Photo photo) {
        Long id = photoService.addPhoto(photo);
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        return id != -1 ?
                ResponseEntity.status(HttpStatus.CREATED).body(map) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Removes photo")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePhoto(@PathVariable final Long id) {
        return photoService.deletePhoto(id) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Updates existing photo")
    @PutMapping("/{id}")
    public ResponseEntity editPhoto(@PathVariable final Long id, @RequestBody final Photo photo) {
        return photoService.editPhoto(id, photo) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Returns number of photos by its share state")
    @GetMapping("/count/{state}")
    public ResponseEntity getPhotosCount(@PathVariable ShareState state) {
        try {
            int count = photoService.getPhotosCount(state, PhotoState.ACTIVE);
            Map<String, Long> map = new HashMap<>();
            map.put("count", new Long(count));
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
