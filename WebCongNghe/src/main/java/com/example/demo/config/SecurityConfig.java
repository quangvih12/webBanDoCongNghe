package com.example.demo.config;

import com.example.demo.Authentication.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.example.demo")
public class SecurityConfig {


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
//                        "/loginGoogle/gg",
                        "/view/**",
                        "/api/v1/auth/**",
                        "/js/**", "/css/**",
                        "/img/**",
                        "/api/product/**",
                        "/api/gioHang/**",
                        "/api/hoaDon/**",
                        "/api/hoaDonD/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .loginPage("/login") // Đường dẫn đến trang đăng nhập
                .defaultSuccessUrl("/view") // Đường dẫn sau khi xác thực thành công
                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .defaultSuccessUrl("/view")
//                .failureForwardUrl("/fail_Login")
//                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());


        return http.build();
    }



}


