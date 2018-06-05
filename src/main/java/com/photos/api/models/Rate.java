package com.photos.api.models;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

//TODO: Add Swagger and Jackson annotations.

@Entity
@Table(name = "rate")
@ApiModel
public class Rate {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "photo_id",
            referencedColumnName = "id"
    )
    private Photo photo;

    @NotNull
    @Column(name = "value")
    private Integer value;

    public Rate() {
    }

    public Rate(
            @NotNull User user,
            @NotNull Photo photo,
            @NotNull Integer value
    ) {
        this.user = user;
        this.photo = photo;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
