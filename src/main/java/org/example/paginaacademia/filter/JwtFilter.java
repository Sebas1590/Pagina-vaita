package org.example.paginaacademia.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.paginaacademia.service.UserDetailsServiceImpl;
import org.example.paginaacademia.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        try {
            if(cookies != null){
                Arrays
                        .stream(cookies)
                        .filter(cookie -> cookie.getName().equals("token"))
                        .findFirst()
                        .ifPresent(
                                cookie -> {
                                    String token = cookie.getValue();
                                    String username = jwtUtil.extractUsername(token);
                                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                                        UsernamePasswordAuthenticationToken authToken =
                                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                        SecurityContextHolder.getContext().setAuthentication(authToken);
                                    }
                                }

                        );
            }

            filterChain.doFilter(request, response);

        }catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token expirado\"}");
            return;
        }catch (JwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token inválido\"}");
            return;
        }
    }

}
