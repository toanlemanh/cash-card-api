package com.example.cash_card_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    /*
     @Note:
      Because in this course we follow test-driven approach (test first and not using non-browser testing)
      so we doesn't need CSRF => disable
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/cashcards/**")
                        .hasRole("user")
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf->csrf.disable())
                .build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Using UserDetailService to test only for several users
    @Bean
    UserDetailsService testOnlyUsers (PasswordEncoder passwordEncoder) {
        // Using UserBuilder to build users
        // UserDetails object hold username, password and role of user.
        User.UserBuilder usersBuilder = User.builder();
        UserDetails toan123 = usersBuilder
                .username("toan")
                .password(passwordEncoder.encode("123"))
                .roles("user")
                .build();

        UserDetails amee123 = usersBuilder
                .username("amee")
                .password(passwordEncoder.encode("123"))
                .roles("user", "admin")
                .build();
        UserDetails jack123 = usersBuilder
                .username("jack")
                .password(passwordEncoder.encode("123"))
                .roles("customer")
                .build();
        return new InMemoryUserDetailsManager(toan123, amee123, jack123 );
    }


}
