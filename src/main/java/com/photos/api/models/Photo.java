package com.photos.api.models;

import com.photos.api.models.enums.PhotoState;
import com.photos.api.models.enums.PhotoVisibility;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Micha Królewski on 2018-04-07.
 * @version 1.0
 */

//TODO: Add Swagger and Jackson annotations.

@Entity
@Table(name = "photo")
@ApiModel
public class Photo {
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
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @Column(name = "path")
    private String path;

    @Column(name = "description")
    private String description;

    @Column(name = "visibility")
    private PhotoVisibility visibility;

    @Column(name = "state")
    private PhotoState state;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "photo_to_category",
            joinColumns = {@JoinColumn(name = "photo_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "share",
            joinColumns = {@JoinColumn(name = "photo_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<Share> shares = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "photo"
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "photo"
    )
    private Set<Rate> rates = new HashSet<>();

    public Photo() {
    }

    public Photo(
            @NotNull String name,
            @NotNull User user,
            String path,
            String description,
            PhotoVisibility visibility,
            PhotoState state
    ) {
        this.name = name;
        this.user = user;
        this.path = path;
        this.description = description;
        this.visibility = visibility;
        this.state = state;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PhotoVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(PhotoVisibility visibility) {
        this.visibility = visibility;
    }

    public PhotoState getState() {
        return state;
    }

    public void setState(PhotoState state) {
        this.state = state;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }
}
