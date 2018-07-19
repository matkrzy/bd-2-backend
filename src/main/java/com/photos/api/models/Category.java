package com.photos.api.models;

import com.fasterxml.jackson.annotation.*;
import com.photos.api.resolvers.EntityIdResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Micha Królewski on 2018-04-08.
 * @version 1.0
 */

@Entity
@Table(name = "category")
@ApiModel
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope = Category.class
)
public class Category {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    @ApiModelProperty(required = true)
    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    @ApiModelProperty(readOnly = true)
    private Date creationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonProperty("userId")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(required = true, dataType = "int")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonProperty("parentId")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "int")
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @JsonProperty("childrenIds")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "[I")
    private Set<Category> children = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "photo_to_category",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = {@JoinColumn(name = "photo_id")}
    )
    @JsonProperty("photoIds")
    @JsonIdentityReference(alwaysAsId = true)
    @ApiModelProperty(dataType = "[I")
    private Set<Photo> photos = new HashSet<>();

    public Category() {
    }

    public Category(
            @NotNull String name,
            @NotNull User user,
            Category parent
    ) {
        this.name = name;
        this.user = user;
        this.parent = parent;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}
