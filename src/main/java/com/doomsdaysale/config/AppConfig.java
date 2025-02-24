// this file contains the configuration of web application.
package com.doomsdaysale.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration // this indicates that this class is a configuration class and is a part of core spring framework and contains bean definitions and configurations.
@EnableWebSecurity // Enables Spring Security for the web application. It tells Spring to look for a class to configure web security settings.
public class AppConfig {
    @Bean
    // this is used to create the application communication restful or stateless where jwt token is used to authenticate the user. By default spring boot uses session based authentication we need to make to RESTFUL.
    SecurityFilterChain securityFilterChain (HttpSecurity http)throws Exception{
        http.sessionManagement(management->management.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        )).authorizeHttpRequests(authorize->authorize
        .requestMatchers("/api/**").authenticated() // **-> any level like api/product/cart/review.
                        .requestMatchers("/api/products/*/reviews").permitAll() // only one level like api/product/comment.
                        .anyRequest().permitAll()
            ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable()) // disables cross-site request forgery attacks in application
                .cors(cors->cors.configurationSource(corsConfigurationSource())); // this will enable our frontend to access backend.
                return http.build();

            // the addFilterBefore() method is used to execute a custom JwtTokenValidator() method before BasicAuthenticationFilter.
    }

    // this is a custom method which defines the cross-origin resource sharing i.e. between client and server.
    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Collections.singletonList("*")); //here we should specify all the end points of front end which should be able to access using arraylist the DB.
                cfg.setAllowedMethods(Collections.singletonList("*")); // this will allow every HTTP method to work on the DB.
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));// this will allow all the headers.
                cfg.setExposedHeaders(Collections.singletonList("*"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    @Bean
    // this will encode the password before saving.
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    // this will help in communicating with different apis.
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
