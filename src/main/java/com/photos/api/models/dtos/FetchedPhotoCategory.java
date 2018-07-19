package com.photos.api.models.dtos;

import com.photos.api.models.Category;
import com.photos.api.models.Photo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel
public class FetchedPhotoCategory {
    @NotNull
    @ApiModelProperty(required = true, dataType = "int")
    private Long id;

    @NotNull
    @ApiModelProperty(required = true, dataType = "String")
    private String name;

    public FetchedPhotoCategory() {
    }

    public FetchedPhotoCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();
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
}
