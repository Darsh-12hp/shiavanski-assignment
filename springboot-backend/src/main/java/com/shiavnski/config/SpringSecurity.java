package com.shiavnski.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shiavnski.filter.JwtFilter;
import com.shiavnski.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{ 
       httpSecurity
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/employees/**").authenticated()  // Authenticated users can edit/delete employees
            .requestMatchers("/api/v1/allemployees", "/api/v1/empsignup", "/api/v1/emplogin").permitAll()  // Public access to these endpoints
            .anyRequest().permitAll() // Permit all other requests
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session
        )
        .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (since using stateless JWT-based auth)
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter to validate token
    
    return httpSecurity.build();
}

@  Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder using BCrypt
    }
    

    

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    
    authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(passwordEncoder());
    
    return authenticationManagerBuilder.build();  // Call build() on AuthenticationManagerBuilder directly
}

}
