package com.SSR.Config;

import com.SSR.Entity.App;
import com.SSR.Repository.AppRepository;
import com.SSR.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private AppRepository appRepository;

    public JWTFilter(JWTService jwtService, AppRepository appRepository) {
        this.jwtService = jwtService;
        this.appRepository = appRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token!= null && token.startsWith("Bearer ")) {
            String substring = token.substring(8, token.length() - 1);
            String username = jwtService.getUsername(substring);
            //System.out.println(username); //
            Optional<App> opUsername = appRepository.findByUsername(username);
            if (opUsername.isPresent()){
                App app = opUsername.get();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken
                        (app,null,null);
                auth.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }
    filterChain.doFilter(request, response);
        
    }
}
