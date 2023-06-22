package com.example.minipropertymanagement.config;


import com.example.minipropertymanagement.enums.Role;
import com.example.minipropertymanagement.filter.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Config {


    private final JWTFilter jwtFilter;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrfConfigurer -> csrfConfigurer.disable()).authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll();
            authorize.requestMatchers("/api/v1/admin**").hasAuthority(Role.ADMIN.getRole());
            authorize.requestMatchers(HttpMethod.GET, "/api/v1/users").authenticated();
            authorize.requestMatchers(HttpMethod.POST, "/api/v1/properties").hasAuthority(Role.OWNER.getRole());
            authorize.requestMatchers(HttpMethod.POST, "/api/v1/properties/*/offers").hasAuthority(Role.USER.getRole());
            authorize.requestMatchers(HttpMethod.GET, "/api/v1/properties/*/offers").hasAnyAuthority(Role.USER.getRole(), Role.OWNER.getRole());
            authorize.requestMatchers(HttpMethod.GET, "/api/v1/properties").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/api/v1/properties/{propertyId}").permitAll();
            authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/properties/{propertyId}").hasAuthority(Role.OWNER.getRole());
            authorize.requestMatchers("/api/v1/properties/{propertyId}/favorites").hasAnyAuthority(Role.USER.getRole());
            authorize.requestMatchers("/api/v1/favorites").hasAnyAuthority(Role.USER.getRole());
            authorize.requestMatchers(HttpMethod.GET, "/api/v1/offers").hasAnyAuthority(Role.USER.getRole(), Role.OWNER.getRole());
            authorize.requestMatchers(HttpMethod.PUT, "/api/v1/offers/{offerId}**").hasAnyAuthority(Role.USER.getRole(), Role.OWNER.getRole());
        }).sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedHeaders("*").exposedHeaders("*").allowedMethods("*").allowedOrigins("*");
            }
        };

    }
}
