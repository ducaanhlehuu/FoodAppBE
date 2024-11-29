package com.shop.food.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
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
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private  final JWTService jwtService;
    private  final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;
        String path = request.getServletPath();

        if (path.startsWith("/api/user/auth") || path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Authorization header is missing or invalid.", "No or invalid token provided.");
            return;
        }

        jwtToken = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUserName(jwtToken); // Lấy thông tin từ JWT

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isValidToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT token has expired. Please login again.", ex.getMessage());
        } catch (Exception ex) {
            handleException(response, HttpServletResponse.SC_FORBIDDEN, "Authentication failed.", ex.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, int status, String message, String details) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format(
                "{ \"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"details\": \"%s\" }",
                java.time.LocalDateTime.now(), status, message, details
        ));
        response.getWriter().flush();
    }

}
