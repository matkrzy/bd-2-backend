package com.photos.api.controllers;

import com.photos.api.services.ImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Micha Królewski on 2018-04-29.
 * @version x
 */

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ApiOperation(value = "Returns image")
    @GetMapping("/{photoId}")
    public ResponseEntity getImage(@PathVariable Long photoId) {

        try {
            Resource file = imageService.findImage(photoId);
            return file != null ?
                    ResponseEntity.status(HttpStatus.OK).contentLength(file.contentLength()).contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(file.getInputStream())) :
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Couldn't find " + photoId);
        }
    }

    @ApiOperation(value = "Creates new image")
    @PostMapping("/{photoId}")
    public ResponseEntity addImage(@RequestParam("file") MultipartFile file, @PathVariable Long photoId) throws IOException {
        return imageService.createImage(file,photoId) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
