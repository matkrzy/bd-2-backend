package com.photos.api.models;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

//TODO: Add Swagger annotations.

@Entity
@Table(name = "`like`", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "photo_id"})})
@ApiModel
public class Like {
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonProperty("userId")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "photo_id")
    @JsonProperty("photoId")
    @JsonIdentityReference(alwaysAsId = true)
    private Photo photo;

    public Like() {
    }

    public Like(
            @NotNull User user,
            @NotNull Photo photo
    ) {
        this.user = user;
        this.photo = photo;
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
}