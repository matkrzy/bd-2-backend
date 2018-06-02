package com.photos.api.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.photos.api.security.SecurityConstants.JWT;
import static com.photos.api.security.SecurityConstants.getLoggedUserEmail;

/**
 * @author Micha Królewski on 2018-04-21.
 * @version x
 */


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        String url = request.getRequestURI();
        if (url.equals("/api/v1/account/logout")) {
            logout(cookies);
            return;
        }
        UsernamePasswordAuthenticationToken userAuth = getAuthenticationToken(cookies);
        if (userAuth == null) {
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(userAuth);
        chain.doFilter(request, response);
    }

    private void logout(Cookie[] cookies) {
        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JWT)) {
                token = cookie.getValue();
                break;
            }
        }
        customUserDetailsService.destroyToken(token);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(Cookie[] cookies) {

        String email = "";
        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JWT)) {
                email = getLoggedUserEmail(cookie);
                token = cookie.getValue();
                System.out.println(email.toUpperCase());
                break;
            }
        }

        if (customUserDetailsService.isTokenActive(token)) {
            if (email.equals("")) {
                return null;
            }
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            return userDetails != null ? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
        }
        return null;
    }
}
