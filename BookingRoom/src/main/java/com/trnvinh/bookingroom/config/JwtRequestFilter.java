package com.trnvinh.bookingroom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trnvinh.bookingroom.model.User;
import com.trnvinh.bookingroom.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String userName = null;
        String apiToken = null;

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String clientIp = request.getRemoteAddr();  // Lấy địa chỉ IP của client

        // Thêm log để xác minh
//        logger.info("Request URI: " + requestURI);
//        logger.info("Request Method: " + requestMethod);
        logger.info("Client IP: " + clientIp + " - Request URI:" + requestMethod + " " + requestURI);
     // Bỏ qua endpoint đăng ký
        if (requestURI.startsWith("/api/v1/users") && requestMethod.equals(HttpMethod.POST.name())
        		|| requestURI.startsWith("/api/v1/users/check/{userName}")) {
            logger.info("Skipping JWT filter for: " + requestURI + " | client ip " + clientIp );
            chain.doFilter(request, response);
            return;
        }
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            apiToken = requestTokenHeader.substring(7);
            try {
                User user = userService.findByApiToken(apiToken);
                userName = user != null ? user.getUserName() : null;
            } catch (Exception e) {
                logger.warn("Client IP: " + clientIp + " Unable to get API Token");
            }
        } 
//        else {
//            logger.warn("Client IP: " + clientIp +" API Token does not begin with Bearer String");
//        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.findByUserName(userName);

            if (user != null) {
                org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                        userName, "", user.getIsAdmin()
                                ? AuthorityUtils.createAuthorityList("ROLE_ADMIN")
                                : AuthorityUtils.createAuthorityList("ROLE_USER"));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}
 