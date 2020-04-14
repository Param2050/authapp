package com.temp.authapp.filters;

import com.temp.authapp.service.CustomUserDetailsService;
import com.temp.authapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.temp.authapp.util.Constants.*;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

    final String authorizationHeader = request.getHeader(AUTHORIZATION);

    String userName = null;
    String jwtToken = null;

    if(authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
        jwtToken = authorizationHeader.substring(INDEX);
        userName = jwtUtil.extractUsername(jwtToken);
    }

    if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailService.loadUserByUsername(userName);

        if(jwtUtil.validateToken(jwtToken, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
                            userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    filterChain.doFilter(request, response);

    }
}
