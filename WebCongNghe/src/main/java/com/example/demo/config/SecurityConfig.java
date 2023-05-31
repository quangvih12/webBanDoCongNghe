package com.example.demo.config;

import com.example.demo.Authentication.JwtAuthenticationTokenFilter;

import com.example.demo.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.example.demo")
public class SecurityConfig {

    @Autowired
    AuthenticationSuccessHandler successHandler;

    private final JwtAuthenticationTokenFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/login",
                        "/logout",
                        "/tests",
                        "/view/**",
                        "/Admin/**",
                        "/api/v1/auth/**",
                        "/js/**", "/css/**",
                        "/img/**",
                        "/api/product/**",
                        "/api/gioHang/**",
                        "/api/hoaDon/**",
                        "/api/BillAdmin/**","/api/productAdmin/**",
                        "/api/hoaDonD/**"
                )
                .permitAll()
//                .requestMatchers("/api/BillAdmin/**", "/api/productAdmin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin().loginPage("/login").permitAll()
//                .defaultSuccessUrl("/view")
//                .failureForwardUrl("/fail_Login")
//                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .and().oauth2Login().loginPage("/login")
                .successHandler(successHandler);


        return http.build();
    }


}


