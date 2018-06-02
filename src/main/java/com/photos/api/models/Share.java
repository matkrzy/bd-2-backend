package com.photos.api.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

@Entity
@Table(name = "share")
@ApiModel
public class Share {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long shareID;

    @NotNull
    @OneToOne
    @JoinColumn(name = "photo")
    private Photo photo;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    @OneToOne
    @JoinColumn(name = "owner")
    private User owner;

    public Share() {
    }

    public Share(Long id) {
        this.shareID = id;
    }

    public Share(@NotNull Photo photo, @NotNull User user) {
        this.photo = photo;
        this.user = user;
    }

    @ApiModelProperty(readOnly = true)
    public Long getShareID() {
        return shareID;
    }

    public void setShareID(Long shareID) {
        this.shareID = shareID;
    }

    @ApiModelProperty(readOnly = true)
    public User getUser() {
        return user;
    }

    @ApiModelProperty(required = true)
    public void setUser(User user) {
        this.user = user;
    }

    @ApiModelProperty(hidden = true)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ApiModelProperty(readOnly = true)
    public Photo getPhoto() {
        return photo;
    }

    @ApiModelProperty(required = true)
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @ApiModelProperty(readOnly = true)
    public Long getphoto_id() {
        return photo.getPhotoID();
    }

    @ApiModelProperty(readOnly = true)
    public String getuser_email() {
        return user.getEmail();
    }

    @ApiModelProperty(readOnly = true)
    public String getowner_email() {
        return owner.getEmail();
    }
}


