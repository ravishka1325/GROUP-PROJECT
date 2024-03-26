package com.Goappoint.GoAppoint.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/v1/user/saveUser").permitAll()
                .requestMatchers("/api/v1/user/getAllUsers").permitAll()
                .requestMatchers("/api/v1/user/login").permitAll()
                .requestMatchers("/api/v1/user/logout").permitAll()
                .requestMatchers("/api/v1/business/saveBusiness").permitAll()
                .requestMatchers("/api/v1/business/updateBusiness").permitAll()
                .requestMatchers("/api/v1/business/updateBusinessHours").permitAll()
                .requestMatchers("/api/v1/business/uploadBusinessImage").permitAll()
                .requestMatchers("/api/v1/business/login").permitAll()
                .requestMatchers("/api/v1/business/logout").permitAll()
                .requestMatchers("/api/v1/business/getBusiness/{businessId}").permitAll()
                .requestMatchers("/api/v1/business/getBusinessHours/{businessId}").permitAll()
                .requestMatchers("/api/v1/business/getBusinessImage/{businessId}").permitAll()
                .requestMatchers("/api/v1/business/search/**").permitAll()
                .requestMatchers("/api/v1/service/saveService").permitAll()
                .requestMatchers("/api/v1/service/getService/{businessId}").permitAll()
                .requestMatchers("/api/v1/appointment/createAppointment").permitAll()
                .requestMatchers("/api/v1/appointment/getAppointment/{businessId}").permitAll()
                .requestMatchers("/api/v1/appointment/appointmentCount/{businessId}").permitAll()
                .requestMatchers("/api/v1/appointment/getAppointment/user/{userID}").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost")); // Replace with the appropriate origin(s)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}