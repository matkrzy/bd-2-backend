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
@Table(name = "photo_to_category")
@ApiModel
public class PhotoToCategory {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long ptcID;

    @NotNull
    @OneToOne
    @JoinColumn(name = "photo")
    private Photo photo;

    @NotNull
    @OneToOne
    @JoinColumn(name = "category")
    private Category category;

    public PhotoToCategory() {
    }

    public PhotoToCategory(@NotNull Photo photo, @NotNull Category category) {
        this.photo = photo;
        this.category = category;
    }

    public Long getPtcID() {
        return ptcID;
    }

    public void setPtcID(Long ptcID) {
        this.ptcID = ptcID;
    }

    public Photo getPhoto() {
        return photo;
    }

    @ApiModelProperty(required = true)
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Category getCategory() {
        return category;
    }

    @ApiModelProperty(required = true)
    public void setCategory(Category category) {
        this.category = category;
    }
}
