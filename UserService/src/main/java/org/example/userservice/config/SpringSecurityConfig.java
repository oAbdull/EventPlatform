package org.example.userservice.config;

import org.example.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    private final UserService userService;
    private final JwtTokenFilter jwtFilter;

    public SpringSecurityConfig(@Lazy UserService userService, JwtTokenFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        log.info("SpringSecurityConfig initialized with UserService and JwtTokenFilter");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Creating BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring SecurityFilterChain");
        return http
                .csrf(csrf -> {
                    log.debug("Disabling CSRF protection");
                    csrf.disable();
                })
                .authorizeHttpRequests(auth -> {
                    log.debug("Configuring request authorization");
                    auth
                            .requestMatchers("/api/users/register", "/api/users/validate", "/api/users/validate-test").permitAll()
                            .requestMatchers("/api/users/profile").authenticated()
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    log.debug("Configuring session management as stateless");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    log.debug("Configuring exception handling");
                    ex
                            .authenticationEntryPoint((request, response, authException) -> {
                                log.warn("Unauthorized access: {}", authException.getMessage());
                                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                            })
                            .accessDeniedHandler((request, response, accessDeniedException) -> {
                                log.warn("Access denied: {}", accessDeniedException.getMessage());
                                response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
                            });
                })
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.info("Creating DaoAuthenticationProvider");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("Creating AuthenticationManager");
        return config.getAuthenticationManager();
    }
}