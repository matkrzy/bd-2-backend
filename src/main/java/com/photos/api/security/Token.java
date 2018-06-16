package com.photos.api.security;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Micha Królewski on 2018-05-27.
 * @version x
 */

@Entity
@Table(name = "token_blacklist")
public class Token {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "expiration")
    private Timestamp expiration;

    public Token() {
    }

    public Token(
            @NotNull String token,
            @NotNull Timestamp expiration
    ) {
        this.token = token;
        this.expiration = expiration;
    }

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
