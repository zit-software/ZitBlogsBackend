package com.zit.blog.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zit.blog.Auth.JWTService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTFilterChain extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        System.out.println("JWT Filter called!");
        if (authorization == null || !authorization.startsWith("Bearer")) {
            // Without setting security context during chain, this request will be blocked later.
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.substring(7);
        String checkTokenMessage = jwtService.isTokenValid(token);
        if (checkTokenMessage != null) {
            sendResponse(response, checkTokenMessage, HttpStatus.FORBIDDEN);
            return;
        }
        String username = jwtService.extractClaim(token, Claims::getSubject);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(userDetails.getUserInfo(), null,
                    userDetails.getAuthorities());
            System.out.println(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private HttpServletResponse sendResponse(HttpServletResponse response, String message, HttpStatus httpStatus) throws IOException {
        response.setStatus(httpStatus.value());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "You are not authenticated");
        response.setContentType("application/json");

        // Write the response body
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        mapper.writeValue(out, responseBody);
        out.flush();
        out.close();
        return response;
    }
}
