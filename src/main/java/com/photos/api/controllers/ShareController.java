package com.photos.api.controllers;

import com.photos.api.models.Share;
import com.photos.api.models.repositories.ShareRepository;
import com.photos.api.services.ShareService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Micha Królewski on 2018-05-05.
 * @version 1.0
 */

@RestController
@RequestMapping("/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareRepository shareRepository;

    @ApiOperation(value = "Creates new share")
    @PostMapping
    public ResponseEntity addShare(@RequestBody final Share share) {
        return shareService.addShare(share) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Reomves share")
    @DeleteMapping
    public ResponseEntity deleteShare(@RequestBody final Share share) {
        return shareService.deleteShare(share) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
