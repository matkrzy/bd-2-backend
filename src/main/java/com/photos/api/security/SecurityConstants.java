package com.photos.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version x
 */


public class SecurityConstants {

    public static final String SECRET = "secret";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String JWT = "JWT";

    /**
     * Session time - ~15min
     */
    // TODO: 2018-05-13 ZMIANA CZASU TOKENU
    public static final Long EXPIRATION_TIME = 903_100_000L;

    /**
     * @param auth
     * @return
     */
    public static String generateToken(Authentication auth) {

        ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS);
        return Jwts.builder().setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .setExpiration(Date.from(expirationTimeUTC.toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static String getLoggedUserEmail(Cookie cookie) {

        String token = cookie.getValue();
        if (token == null)
            return null;

        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().getSubject();
    }

}
