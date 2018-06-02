package com.photos.api.models;

import com.photos.api.models.enums.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@Entity
@Table(name = "user")
@ApiModel

public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long userID;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(Long id) {
        this.userID = id;
    }

    public User(@NotNull String email, @NotNull String firstName, @NotNull String lastName, @NotNull String password, String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    @ApiModelProperty(readOnly = true)
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    @ApiModelProperty(required = true)
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @ApiModelProperty(required = true)
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    @ApiModelProperty(required = true)
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @ApiModelProperty(required = true)
    public void setPassword(String password) {
        this.password = password;
    }

    @ApiModelProperty(readOnly = true)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
