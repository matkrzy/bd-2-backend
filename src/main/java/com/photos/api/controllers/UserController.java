package com.photos.api.controllers;

import com.photos.api.models.User;
import com.photos.api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@RestController
@RequestMapping("/users")
@Api(value = "User resource", description = "Returns users")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Returns users", response = User.class)
    @GetMapping
    public ResponseEntity getUsers() {
        try {
            List<User> users = userService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Creates user")
    @PostMapping
    public ResponseEntity addUser(@RequestBody final User user) {
        try {
            User addedUser = userService.add(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Returns current user", response = User.class)
    @GetMapping("/me")
    public ResponseEntity getCurrent() {
        try {
            User user = userService.getCurrent();

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Returns user by ID", response = User.class)
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable final Long id) {
        try {
            User user = userService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Updates user")
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable final Long id, @RequestBody final User user) {
        if (!id.equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }

        try {
            User updatedUser = userService.update(user);

            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "Removes user")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable final Long id) {
        try {
            userService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
