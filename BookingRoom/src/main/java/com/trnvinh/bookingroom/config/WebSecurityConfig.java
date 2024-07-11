package com.trnvinh.bookingroom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.trnvinh.bookingroom.model.User;
import com.trnvinh.bookingroom.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	private UserService userService;
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/users/check/{userName}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/users/{userId}").permitAll()
				.requestMatchers("/api/v1/users/check/{userName}").permitAll().requestMatchers("/api/v1/users/login")
				.permitAll().requestMatchers("/api/v1/users/logout/{userId}").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/dayoff").permitAll().requestMatchers("/api/v1/bookingroom/**")
				.permitAll().requestMatchers(HttpMethod.GET, "/api/v1/rooms/**").permitAll()
				.requestMatchers("/api/v1/users/**", "/api/v1/rooms/**", "/api/v1/dayoff/**").hasRole("ADMIN")
				.anyRequest().authenticated()).httpBasic(t -> t.realmName("trnvinh"))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	@Bean
	 UserDetailsService userDetailsService() {
		return username -> {
			User user = userService.findByUserName(username);
			if (user == null) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
			return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
					.password(user.getPassword()).authorities(user.getIsAdmin() ? "ROLE_ADMIN" : "ROLE_USER").build();
		};
	}
}
