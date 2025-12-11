package org.ximenia.flashcash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.ximenia.flashcash.service.AuthenticationService;

@Configuration
public class SecurityConfig {

    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .userDetailsService(authenticationService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/bootstrap.min.css",
                                "/signin.css",
                                "/profil.css",
                                "/images/**",
                                "/signup",
                                "/signin"
                        ).permitAll()
                        .requestMatchers("/profil").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/signin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profil", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/signin?logout")
                        .permitAll()
                );

        return http.build();
    }
}
