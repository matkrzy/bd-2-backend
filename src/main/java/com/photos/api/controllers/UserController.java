package com.photos.api.controllers;

import com.photos.api.exceptions.EntityDeleteDeniedException;
import com.photos.api.exceptions.EntityNotFoundException;
import com.photos.api.exceptions.EntityUpdateDeniedException;
import com.photos.api.models.*;
import com.photos.api.services.UserService;
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
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@RestController
@RequestMapping("/users")
@Api(value = "User resource")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Returns users", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity getUsers() {
        try {
            List<User> users = userService.getAll();

            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Creates user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully")
    })
    @PostMapping
    public ResponseEntity addUser(@RequestBody final User user) {
        try {
            User addedUser = userService.add(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns current user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User retrieved successfully")
    })
    @GetMapping("/me")
    public ResponseEntity getCurrent() {
        try {
            User user = userService.getCurrent();

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns user by ID", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User retrieved successfully"),
            @ApiResponse(code = 404, message = "User with given ID doesn't exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable final Long id) {
        try {
            User user = userService.getById(id);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Updates user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 400, message = "Invalid entity given"),
            @ApiResponse(code = 403, message = "No permission to update given user"),
            @ApiResponse(code = 404, message = "User with given ID doesn't exist")
    })
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable final Long id, @RequestBody final User user) {
        if (!id.equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }

        try {
            User updatedUser = userService.update(user);

            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityUpdateDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Deletes user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully"),
            @ApiResponse(code = 403, message = "No permission to delete given user"),
            @ApiResponse(code = 404, message = "User with given ID doesn't exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable final Long id) {
        try {
            userService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EntityDeleteDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns user photos by user ID", response = Photo.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Photos retrieved successfully"),
            @ApiResponse(code = 404, message = "User with given ID doesn't exist")
    })
    @GetMapping("/{id}/photos")
    public ResponseEntity getUserPhotos(@PathVariable final Long id) {
        try {
            Set<Photo> photos = userService.getById(id).getPhotos();

            return ResponseEntity.status(HttpStatus.OK).body(photos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns user categories by user ID", response = Category.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categories retrieved successfully"),
            @ApiResponse(code = 404, message = "User with given ID doesn't exist")
    })
    @GetMapping("/{id}/categories")
    public ResponseEntity getUserCategories(@PathVariable final Long id) {
        try {
            Set<Category> categories = userService.getById(id).getCategories();

            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns user likes by user ID", response = Like.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Likes retrieved successfully"),
            @ApiResponse(code = 404, message = "User with given ID doesn't exist")
    })
    @GetMapping("/{id}/likes")
    public ResponseEntity getUserLikes(@PathVariable final Long id) {
        try {
            Set<Like> likes = userService.getById(id).getLikes();

            return ResponseEntity.status(HttpStatus.OK).body(likes);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Returns user shares by user ID", response = Share.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shares retrieved successfully"),
            @ApiResponse(code = 404, message = "User with given ID doesn't exist")
    })
    @GetMapping("/{id}/shares")
    public ResponseEntity getUserShares(@PathVariable final Long id) {
        try {
            Set<Share> shares = userService.getById(id).getShares();

            return ResponseEntity.status(HttpStatus.OK).body(shares);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
