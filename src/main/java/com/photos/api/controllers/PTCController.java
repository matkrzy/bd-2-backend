package com.photos.api.controllers;

import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.PhotoToCategory;
import com.photos.api.services.PhotoToCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Micha Królewski on 2018-05-22.
 * @version x
 */

@RestController
@RequestMapping("/category")
public class PTCController {

    @Autowired
    private PhotoToCategoryService service;

    @ApiOperation(value = "Assigns category to the photo")
    @PostMapping
    public ResponseEntity setCategory(@RequestBody final PhotoToCategory ptc){
        return service.setCategory(ptc) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // TODO: 2018-06-02 DELETE
    @ApiOperation(value = "Updates existing assignment")
    @PutMapping("/{category}")
    public ResponseEntity setNewCategory(@RequestBody final PhotoToCategory ptc,@ApiParam(required = true, value = "new category id") @PathVariable final Category category){
        return service.setNewCategory(ptc,category) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Removes assignment")
    @DeleteMapping
    public ResponseEntity deletePhotoCategory(@RequestBody final PhotoToCategory ptc){
        return service.deleteCategory(ptc) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
