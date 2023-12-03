package com.example.securityimplement.security;

import com.example.securityimplement.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; //Will be implemented in ApplicationConfig
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); //The token should be withing the header, we will extract it
        final String jwt;
        final String email;

        //Let's check the jwt token
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        //todo : extract user email from the token => we will use JwtService;
        email = jwtService.extractUsername(jwt);
        System.out.println("Looking for email : " + email);
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){ //User not authenticated){
            //Let's get user from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            System.out.println("Found user : " + userDetails.getUsername());
            if (jwtService.isTokenValid(jwt, userDetails)){
                System.out.println("Token is valid 100%");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Principle : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
        filterChain.doFilter(request, response); //As a last step, we need to pass the hand to the next filters


    }
}
