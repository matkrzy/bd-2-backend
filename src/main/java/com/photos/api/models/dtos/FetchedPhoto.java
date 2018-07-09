package com.photos.api.models.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.photos.api.models.*;
import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.PhotoVisibility;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@ApiModel
public class FetchedPhoto {
    @NotNull
    @ApiModelProperty(required = true, dataType = "int")
    private Long id;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String")
    private String name;

    @NotNull
    @ApiModelProperty(readOnly = true, required = true, dataType = "Date")
    private Date creationDate;

    @NotNull
    @ApiModelProperty(required = true, dataType = "int")
    private Long userId;

    @Column(name = "path")
    private String path;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String", allowableValues = "PUBLIC,PRIVATE")
    private PhotoVisibility visibility = PhotoVisibility.PRIVATE;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String", allowableValues = "ARCHIVED,ACTIVE")
    private PhotoState state = PhotoState.ACTIVE;

    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "[Ljava.lang.String")
    private Set<Tag> tags = new HashSet<>();

    @JsonProperty("categoryIds")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "[I")
    private Set<Category> categories = new HashSet<>();

    @JsonProperty("shareIds")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "[I")
    private Set<Share> shares = new HashSet<>();

    @JsonProperty("likeIds")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "[I")
    private Set<Like> likes = new HashSet<>();

    @ApiModelProperty(required = true, dataType = "boolean")
    private boolean liked;

    public FetchedPhoto() {
    }

    public FetchedPhoto(Photo photo, User currentUser) {
        this.id = photo.getId();
        this.name = photo.getName();
        this.creationDate = photo.getCreationDate();
        this.userId = photo.getUser().getId();
        this.path = photo.getPath();
        this.url = photo.getUrl();
        this.description = photo.getDescription();
        this.visibility = photo.getVisibility();
        this.state = photo.getState();
        this.tags = photo.getTags();
        this.categories = photo.getCategories();
        this.shares = photo.getShares();
        this.likes = photo.getLikes();
        this.liked = photo.getLikes().stream().anyMatch((like -> like.getUser() == currentUser));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PhotoVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(PhotoVisibility visibility) {
        this.visibility = visibility;
    }

    public PhotoState getState() {
        return state;
    }

    public void setState(PhotoState state) {
        this.state = state;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
