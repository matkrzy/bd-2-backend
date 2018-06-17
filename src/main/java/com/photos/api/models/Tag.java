package com.photos.api.models;

import com.fasterxml.jackson.annotation.*;
import com.photos.api.resolvers.EntityIdResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

@Entity
@Table(name = "tag")
@ApiModel
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name",
        resolver = EntityIdResolver.class,
        scope = Tag.class
)
public class Tag {
    @Id
    @NotNull
    @Column(name = "name")
    @ApiModelProperty(required = true)
    private String name;

    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    @ApiModelProperty(readOnly = true)
    private Date creationDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "photo_to_tag",
            joinColumns = {@JoinColumn(name = "tag_name")},
            inverseJoinColumns = {@JoinColumn(name = "photo_id")}
    )
    @JsonProperty("photoIds")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "[I")
    private Set<Photo> photos;

    public Tag() {
    }

    public Tag(
            @NotNull String name
    ) {
        this.name = name;
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

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photo) {
        this.photos = photo;
    }
}
