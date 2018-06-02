package com.photos.api.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author Micha Królewski on 2018-05-27.
 * @version x
 */

@Component
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}
