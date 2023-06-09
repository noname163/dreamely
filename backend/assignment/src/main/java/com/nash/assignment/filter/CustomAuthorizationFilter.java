package com.nash.assignment.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    public static final String APPLICATION_JSON_VALUE = "application/json";
    private static final String AUTHORIZATION = "Authorization";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/login")
                || request.getServletPath().equals("")
                || request.getServletPath().equals("/")
                || request.getServletPath().equals("/products")
                || request.getServletPath().equals("/register")
                || request.getServletPath().equals("/resources/**")
                || request.getServletPath().equals("/swagger-ui.html")
                || request.getServletPath().equals("/swagger-ui/**")) {
            filterChain.doFilter(request, response);
        } else {
            String authorrizationHeader = request.getHeader(AUTHORIZATION);
            if (authorrizationHeader != null && authorrizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorrizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("OqLtAAxIu8Xgsy1CkBBuNrEf1w0ZB6cy_qTq53n_Gw4".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String role = decodedJWT.getClaim("Roles").toString();
                    role = role.substring(2, role.length() - 2);
                    Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
                    authority.add(new SimpleGrantedAuthority(role));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, authority);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    throw new TokenExpiredException(e.getMessage());
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }

}
