package com.photos.api.controllers;

import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.ResponsePhoto;
import com.photos.api.models.Tag;
import com.photos.api.models.enums.ShareState;
import com.photos.api.services.PhotoService;
import com.photos.api.services.RateService;
import com.photos.api.services.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Micha Królewski on 2018-05-26.
 * @version x
 */

@RestController
@RequestMapping("/photos")
@Api(description = "Returns private photos")
public class GetPrivatePhotosController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private RateService rateService;

    @Autowired
    private TagService tagService;

    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    @ApiOperation(value = "Returns private photos", response = ResponsePhoto.class)
    @GetMapping
    public ResponseEntity getPhotos() {
        List<ResponsePhoto> responsePhotos = convert(photoService.getAll());

        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getUploadTime().compareTo(o1.getUploadTime()));
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }

    @ApiOperation(value = "Returns private photo by id", response = ResponsePhoto.class)
    @GetMapping("/id/{id}")
    public ResponseEntity getPhoto(@PathVariable final Long id) {
        Photo photo = photoService.getPhoto(id);
        if (photo != null) {
            ResponsePhoto p = new ResponsePhoto(photo,
                    rateService.getPhotoRate(photo),
                    tagService.getPhotoTags(photo));
            return ResponseEntity.status(HttpStatus.OK).body(p);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Returns private photo by name", response = ResponsePhoto.class)
    @GetMapping("/name/{name}")
    public ResponseEntity getPhotos(@PathVariable final String name) {

        List<ResponsePhoto> responsePhotos = convert(photoService.getPhoto(name));
        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getUploadTime().compareTo(o1.getUploadTime()));
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }


    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/

    @ApiOperation(value = "Returns private photos which belongs to any of categories", response = ResponsePhoto.class)
    @GetMapping("/categories/any/{categories}")
    public ResponseEntity getByCategoryAny(@ApiParam(required = true, value = "id1,id2,...") @PathVariable List<Category> categories) {
        List<ResponsePhoto> responsePhotos = convert(photoService.getByCategoryAny(categories));

        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getUploadTime().compareTo(o1.getUploadTime()));
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }

    @ApiOperation(value = "Returns private photos which belongs to all of categories", response = ResponsePhoto.class)
    @GetMapping("/categories/all/{categories}")
    public ResponseEntity getByCategoryAll(@ApiParam(required = true, value = "id1,id2,...") @PathVariable List<Category> categories) {

        List<ResponsePhoto> responsePhotos = convert(photoService.getByCategoryAll(categories));

        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getUploadTime().compareTo(o1.getUploadTime()));
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }


    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/

    @ApiOperation(value = "Returns private photos which belongs to any of tags", response = ResponsePhoto.class)
    @GetMapping("/tags/any/{tags}")
    public ResponseEntity getByTagsAny(@ApiParam(required = true, value = "id1,id2,...") @PathVariable List<Tag> tags) {

        List<ResponsePhoto> responsePhotos = convert(photoService.getByTagsAny(tags, ShareState.PRIVATE));
        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getRate() - o1.getRate());
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }


    @ApiOperation(value = "Returns private photos which belongs to all of tags", response = ResponsePhoto.class)
    @GetMapping("/tags/all/{tags}")
    public ResponseEntity getByTagsAll(@ApiParam(required = true, value = "id1,id2,...") @PathVariable List<Tag> tags) {

        List<ResponsePhoto> responsePhotos = convert(photoService.getByTagsAll(tags, ShareState.PRIVATE));
        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getRate() - o1.getRate());
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }


    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/

    @ApiOperation(value = "Returns private photos which are shared to the current user", response = ResponsePhoto.class)
    @GetMapping("/shared")
    public ResponseEntity getSharedPhotos() {

        List<ResponsePhoto> responsePhotos = convert(photoService.getShared());
        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getUploadTime().compareTo(o1.getUploadTime()));
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }


    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/

    @ApiOperation(value = "Returns private photos with no category assignment", response = ResponsePhoto.class)
    @GetMapping("/nocat")
    public ResponseEntity getNoCategoryPhotos() {

        List<ResponsePhoto> responsePhotos = convert(photoService.getNoCategoryPhotos());
        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getUploadTime().compareTo(o1.getUploadTime()));
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }


    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/

    @ApiOperation(value = "Returns private archived photos", response = ResponsePhoto.class)
    @GetMapping("/archived")
    public ResponseEntity getArchived() {

        List<ResponsePhoto> responsePhotos = convert(photoService.getArchived());
        if (responsePhotos == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        responsePhotos.sort((o1, o2) -> o2.getUploadTime().compareTo(o1.getUploadTime()));
        return ResponseEntity.status(HttpStatus.OK).body(responsePhotos);
    }


    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    /*----------------------------------------------------------*/
    private List<ResponsePhoto> convert(List<Photo> photos) {
        if (photos == null) return null;
        List<ResponsePhoto> responsePhotos = new ArrayList<>();

        for (Photo photo : photos) {
            responsePhotos.add(
                    new ResponsePhoto(photo,
                            rateService.getPhotoRate(photo),
                            tagService.getPhotoTags(photo)));
        }
        return responsePhotos.size() == 0 ? null : responsePhotos;
    }
}
