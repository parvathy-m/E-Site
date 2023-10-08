package com.project.carro.Utils.JWT;

import com.project.carro.Exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    CustomUserDetails userDetailsService;
    @Autowired
    AccessTokenGenerator jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = request.getHeader("Authorization");
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String token=new String(Base64.decodeBase64(request.getHeader("Authorization").getBytes()));
                setAuthenticationContext(token, request);
            }
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid Access Token");
        }
        filterChain.doFilter(request, response);
    }
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public UserDetails getUserDetails(String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        String roles = (String) claims.get("roles");
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(roles));
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        String subject = (String) claims.get(Claims.SUBJECT);

        return new User(subject, userDetails.getPassword(), list);
    }

}
