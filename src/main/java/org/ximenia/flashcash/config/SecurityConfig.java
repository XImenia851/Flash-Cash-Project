package org.ximenia.flashcash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.ximenia.flashcash.service.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // ------------------- Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ------------------- Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // ressources statiques accessibles à tous
                        .requestMatchers(
                                "/bootstrap.min.css",
                                "/signin.css",
                                "/profil.css",
                                "/images/**",
                                "/signup",
                                "/signin"
                        ).permitAll()
                        // toutes les autres requêtes nécessitent une auth
                        .anyRequest().authenticated()
                )
                // ------------------- Form login
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/signin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profil", true) // redirection après connexion
                        .permitAll()
                )
                // ------------------- Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/signin?logout")
                        .permitAll()
                )
                // ------------------- UserDetailsService // service qui charge les users depuis la BDD
                .userDetailsService(authenticationService);

        return http.build();
    }
}
