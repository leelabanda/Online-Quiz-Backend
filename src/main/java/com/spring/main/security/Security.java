package com.spring.main.security; // Make sure this is scanned by your main app

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.*;
@Configuration
@EnableWebSecurity
public class Security{
	 @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http
    			.cors(Customizer.withDefaults())
    	        .csrf(csrf -> csrf.disable())
    	        .authorizeHttpRequests(auth -> auth
    	                    // Login & Register
    	                    .requestMatchers("/api/auth/register","/api/users/**","/api/auth/login","/api/question/**","/api/attempt/**","/api/answer/**"
    	                    		,"/api/student/**","/api/quiz/**").permitAll()

    	                    // Admin APIs
    	                    .requestMatchers("/api/role/admin/**").hasAuthority("ADMIN")

    	                    // Student APIs
    	                    .requestMatchers("/api/role/student/**").hasAuthority("STUDENT")

    	                    // Any authenticated user
    	                    .anyRequest().authenticated())
//    	        .formLogin(Customizer.withDefaults())
    	        .httpBasic(Customizer.withDefaults())
    	        .addFilterBefore(
    	                jwtAuthenticationFilter,
    	                UsernamePasswordAuthenticationFilter.class
    	            )
    	        .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("user")
//            .password(passwordEncoder().encode("123#"))
//            .build();
//        return new InMemoryUserDetailsManager(admin);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
    //NoOpPasswordEncode.getInstance()-->default value it will check
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
}