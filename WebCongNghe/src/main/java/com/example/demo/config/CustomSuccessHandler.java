package com.example.demo.config;


import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.reponstory.KhachHangReponsitory;
import com.example.demo.service.impl.KhachHangServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private KhachHangReponsitory khachHangReponsitory;

    @Autowired
    private KhachHangServiceImpl khachHangService;

    public static String name;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = null;
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
            String username = userDetails.getAttribute("email") != null ? userDetails.getAttribute("email") : userDetails.getAttribute("login") + "@gmail.com";
            name = username;
            if (khachHangReponsitory.findByEmail(username) == null) {
                KhachHang user = new KhachHang();
                user.setEmail(username);
                user.setTen(userDetails.getAttribute("name") != null ? userDetails.getAttribute("name") : userDetails.getAttribute("login"));
                user.setMatKhau(("1234"));
                user.setRole(Role.USER);
                khachHangService.save(user);
            } else System.out.println("no");
        }
        redirectUrl = "/view";
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    public static String nameLogin() {
        return name;
    }
}
