package com.antsrl.jwtexample.config;


import com.antsrl.jwtexample.service.JwtService;
import com.antsrl.jwtexample.service.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_PREFIX = "Bearer ";
    private static final int AUTHORIZATION_PREFIX_LENGTH = AUTHORIZATION_PREFIX.length();

    private final JwtService jwtService;
    private final UsersService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith(AUTHORIZATION_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(AUTHORIZATION_PREFIX_LENGTH);

        username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, user)) {
                log.debug("Token is valid {} with user {}", jwt, user);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
