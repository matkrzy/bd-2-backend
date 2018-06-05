package com.photos.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

//TODO: Add Swagger and Jackson annotations.

@Entity
@Table(
        name = "tag",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"photo_id", "name"})}
)
@ApiModel
public class Tag {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "photo_id",
            referencedColumnName = "id"
    )
    private Photo photo;

    public Tag() {
    }

    public Tag(
        @NotNull String name,
        @NotNull Photo photo
    ) {
        this.name = name;
        this.photo = photo;
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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
