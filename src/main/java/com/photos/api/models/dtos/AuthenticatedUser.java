package com.photos.api.models.dtos;

import com.photos.api.models.*;
import com.photos.api.models.enums.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
public class AuthenticatedUser {
    @NotNull
    @ApiModelProperty(required = true, dataType = "int")
    private Long id;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String")
    private String email;

    @NotNull
    @ApiModelProperty(readOnly = true, required = true, dataType = "Date")
    private Date creationDate;

    @NotNull
    @ApiModelProperty(readOnly = true, required = true, dataType = "String")
    private String uuid;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String")
    private String firstName;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String")
    private String lastName;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String", allowableValues = "USER,ADMIN")
    private UserRole role = UserRole.USER;

    public AuthenticatedUser() {
    }

    public AuthenticatedUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.creationDate = user.getCreationDate();
        this.uuid = user.getUuid();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
