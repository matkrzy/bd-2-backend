package com.photos.api.controllers;

import com.photos.api.models.User;
import com.photos.api.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Returns all users", response = User.class)
    @GetMapping("/all")
    public ResponseEntity getAll() {
        List<User> users = userService.getAll();
        return users != null ?
                ResponseEntity.status(HttpStatus.OK).body(users) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ApiOperation(value = "Returns user by email | or user id if it is not logger user", response = User.class)
    @GetMapping("/{email}")
    public ResponseEntity getOne(@PathVariable final String email) {
        User user = userService.getOne(email);
        return user != null ?
                ResponseEntity.status(HttpStatus.OK).body(user) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Creates new user")
    @PostMapping
    public ResponseEntity addUser(@RequestBody final User user) throws IOException {
        return userService.addUser(user) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "Updates existing user")
    @PutMapping
    public ResponseEntity updateUser(@RequestBody final User user) {
        return userService.updateUser(user) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ApiOperation(value = "Removes user")
    @DeleteMapping
    public ResponseEntity deleteUser() {
        return userService.deleteUser() ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
