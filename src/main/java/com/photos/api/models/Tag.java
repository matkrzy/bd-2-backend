package com.photos.api.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */


@Entity
@Table(name = "tag")
@ApiModel
public class Tag {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long tagID;

    @NotNull
    @OneToOne
    @JoinColumn(name = "photo")
    private Photo photo;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    @NotNull
    @Column(name = "name")
    private String name;

    public Tag() {
    }

    public Tag(@NotNull Photo photo, @NotNull User user, @NotNull String name) {

        this.photo = photo;
        this.user = user;
        this.name = name;
    }

    @ApiModelProperty(readOnly = true)
    public Long getTagID() {
        return tagID;
    }

    public void setTagID(Long tagID) {
        this.tagID = tagID;
    }

    @JsonIgnore
    public Photo getPhoto() {
        return photo;
    }

    @JsonProperty
    @ApiModelProperty(hidden = true, required = true)
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Long getphoto_id() {
        return photo.getPhotoID();
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonProperty
    @ApiModelProperty(hidden = true)
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(required = true)
    public void setName(String name) {
        this.name = name;
    }
}
