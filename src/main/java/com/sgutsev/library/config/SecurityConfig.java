package com.sgutsev.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/icon/**")).permitAll()
                        .requestMatchers("/library/home").permitAll()
                        .requestMatchers("/library/librariesInfo").permitAll()
                        .requestMatchers("/library/sign_up").permitAll()
                        .requestMatchers("/library/authors").permitAll()
                        .requestMatchers("/library/newAuthor").authenticated()
                        .requestMatchers("/account/**").authenticated()
                        .requestMatchers("/library/deleteAuthor/**").authenticated()
                        .requestMatchers("/library/changeAuthor/**").authenticated()
                        .requestMatchers("/library/deleteComment").authenticated()
                        .requestMatchers("/library/newComment").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login.loginPage("/library/login").defaultSuccessUrl("/library/home").failureUrl("/library/login?error=true").permitAll())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/library/home").permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
