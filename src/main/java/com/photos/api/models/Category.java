package com.photos.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

@Entity
@Table(name = "category")
@ApiModel
public class Category {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long categoryID;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    @OneToOne
    @JoinColumn(name = "parent_category")
    private Category parentCategory;

    public Category() {
    }

    public Category(Long id) {
        this.categoryID = id;
    }

    public Category(@NotNull String name, @NotNull User user, Category parentCategory) {
        this.name = name;
        this.user = user;
        this.parentCategory = parentCategory;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    @ApiModelProperty(readOnly = true)
    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(required = true, example = "My Category")
    public void setName(String name) {
        this.name = name;
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

    @JsonIgnore
    public Category getParentCategory() {
        return parentCategory;
    }

    @JsonProperty
    @ApiModelProperty(required = true, example = "1000")
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @ApiModelProperty(readOnly = true)
    public String getparent_name() {
        return parentCategory != null ? parentCategory.getName() : null;
    }

    @ApiModelProperty(readOnly = true)
    public Long getparent_id() {
        return parentCategory != null ? parentCategory.getCategoryID() : null;
    }
}



