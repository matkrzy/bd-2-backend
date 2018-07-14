package com.photos.api.models.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import com.photos.api.models.User;
import com.photos.api.resolvers.EntityIdResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

@ApiModel
public class FetchedCategory {
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
    @ApiModelProperty(required = true, dataType = "FetchedUser")
    private FetchedUser user;

    @ApiModelProperty(dataType = "int")
    private Long parentId;

    @ApiModelProperty(dataType = "[I")
    private Set<Long> childrenIds = new HashSet<>();

    @ApiModelProperty(dataType = "[I")
    private Set<Long> photoIds = new HashSet<>();

    public FetchedCategory() {
    }

    public FetchedCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.creationDate = category.getCreationDate();
        this.user = new FetchedUser(category.getUser());
        this.parentId = category.getParent() == null ? null : category.getParent().getId();
        this.childrenIds = category.getChildren().stream().map(Category::getId).collect(Collectors.toSet());
        this.photoIds = category.getPhotos().stream().map(Photo::getId).collect(Collectors.toSet());
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

    public FetchedUser getUser() {
        return user;
    }

    public void setUser(FetchedUser user) {
        this.user = user;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<Long> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(Set<Long> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public Set<Long> getPhotoIds() {
        return photoIds;
    }

    public void setPhotosIds(Set<Long> photoIds) {
        this.photoIds = photoIds;
    }
}
