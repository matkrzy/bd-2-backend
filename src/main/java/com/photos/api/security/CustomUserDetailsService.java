package com.photos.api.security;

import com.photos.api.models.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

import static com.photos.api.security.SecurityConstants.SECRET;
import static com.photos.api.security.SecurityConstants.TOKEN_PREFIX;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version x
 */

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public boolean isTokenActive(String token) {
        Token isActive = tokenRepository.findByToken(token);
        return isActive == null;
    }

    public boolean destroyToken(String token) {
        try {
            Date expiration = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody().getExpiration();

            Token destroyedToken = new Token();

            destroyedToken.setExpiration(Timestamp.from(expiration.toInstant()));
            destroyedToken.setToken(token);
            tokenRepository.save(destroyedToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.photos.api.models.User user = userRepository.findByEmail(email);
        // TODO: 2018-04-21 passwordEncoder
        return user != null ? new User(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole())) : null;
    }
}
