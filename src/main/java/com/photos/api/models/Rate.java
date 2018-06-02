package com.photos.api.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

@Entity
@Table(name = "rate")
@ApiModel
public class Rate {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long rateID;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    @NotNull
    @OneToOne
    @JoinColumn(name = "photo")
    private Photo photo;

    @NotNull
    @Column(name = "date")
    private Timestamp date;

    public Long getRateID() {
        return rateID;
    }

    @ApiModelProperty(hidden = true)
    public void setRateID(Long rateID) {
        this.rateID = rateID;
    }

    public User getUser() {
        return user;
    }

    @ApiModelProperty(hidden = true)
    public void setUser(User user) {
        this.user = user;
    }

    public Photo getPhoto() {
        return photo;
    }

    @ApiModelProperty(required = true, example = "1000")
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Timestamp getDate() {
        return date;
    }

    @ApiModelProperty(hidden = true)
    public void setDate(Timestamp date) {
        this.date = date;
    }

}
