package com.example.demo.service.impl;

import com.example.demo.config.CustomSuccessHandler;
import com.example.demo.entity.KhachHang;
import com.example.demo.reponstory.KhachHangReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginGoogleServiceImpl {

    @Autowired
    private KhachHangReponsitory khachHangReponsitory;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    public static Integer idUser;

    public void  getLooginGG(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) principal;
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
        } else {
            KhachHang khachHang = khachHangReponsitory.findByEmail(customSuccessHandler.nameLogin());
            if (khachHang != null) {
                idUser = khachHang.getId();
                model.addAttribute("userDetails", khachHang.getTen());
            }
        }
    }

    public static Integer getIdUser() {
        return idUser;
    }
}
