package com.shyam.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shyam.config.custom.AuthEntryPoint;
import com.shyam.config.custom.MyAccessDeniedHandler;
import com.shyam.config.custom.MyUserDetailsService;
import com.shyam.filters.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyAccessDeniedHandler accessDeniedHandler;
    private final MyUserDetailsService myUserDetailsService;
    private final AuthEntryPoint authEntryPoint;
    private final JwtAuthFilter jwtAuthFilter;

    @Value("${application.cors.allowedMethods}")
    private List<String> allowedMethods;
    
    @Value("${application.cors.allowedOrigins}")
    private List<String> allowedOrigins;

    @Value("${application.cors.allowedHeaders}")
    private List<String> allowedHeaders;

    private final String[] WHITELIST_AUTH_URLS = {

         // -- Swagger UI v2
         "/v2/api-docs",
         "/swagger-resources",
         "/swagger-resources/**",
         "/configuration/ui",
         "/configuration/security",
         "/swagger-ui.html",
         "/webjars/**",

         // -- Swagger UI v3 (OpenAPI)
         "/v3/**",
         "/swagger-ui/**",

         // other public endpoints of your API may be appended to this array
         "/public",
         "/api/v1/auth/**",
         "/api/v1/home/**",
         "/h2-console/**"
    };

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.csrf(
                AbstractHttpConfigurer::disable
        );

        security.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        security.authorizeHttpRequests(
                authorizer -> authorizer
                                .requestMatchers(WHITELIST_AUTH_URLS).permitAll()
                                .requestMatchers("/api/v1/medicine/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
        );

        security.sessionManagement(
                session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        security.addFilterBefore(
                jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        security.exceptionHandling(
            exception -> exception
                            .authenticationEntryPoint(authEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler)
        );

        return security.build();
    }

}
