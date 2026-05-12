package org.ximenia.flashcash.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.ximenia.flashcash.service.AuthenticationService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Ajouter le bean injecté + la config
    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/images/**", "/signin", "/signup", "/signin.css", "/bootstrap.min.css")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form-> form
                        .loginPage("/signin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profil",true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
    http.userDetailsService(authenticationService);
    return http.build();

    }
}