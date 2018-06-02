package com.photos.api.security;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Micha Królewski on 2018-05-27.
 * @version x
 */

@Entity
@Table(name = "blacklist")
public class Token {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration")
    private Timestamp expiration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }
}
