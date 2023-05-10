package com.example.course_work.Config;


import com.example.course_work.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() { return userDetailsService;}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/login").anonymous()
                    .requestMatchers("/users/create").permitAll()
                    .requestMatchers(HttpMethod.POST, "/products/rate/**", "/orders/**")
                    .hasAnyAuthority("ADMIN", "USER")
                    .requestMatchers("/resources/**", "/static/**", "/styles/**", "/img/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/products/**", "/categories", "/users/delete/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET).permitAll()
                    .anyRequest().authenticated())
            .formLogin().permitAll().defaultSuccessUrl("/mvc/home")
            .and().logout().permitAll().and().httpBasic();
        http.cors().disable().csrf().disable();
        http.userDetailsService(userDetailsService());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}