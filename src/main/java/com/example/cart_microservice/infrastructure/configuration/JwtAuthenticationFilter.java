package com.example.cart_microservice.infrastructure.configuration;

import com.example.cart_microservice.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String JWT_SECRET = System.getenv(Constants.TOKEN_KEY);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest  request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);
        }
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userName = claims.getSubject();
            String role = claims.get("role", String.class);
            Long userId = claims.get("User_id", Long.class);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    userName,
                    "",
                    List.of(authority)
            );

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(userId);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        catch (Exception e) {
            logger.error(Constants.FAILED + e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.TOKEN_START_WITH)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
