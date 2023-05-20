package com.example.demo.controller;


import com.example.demo.config.CustomSuccessHandler;
import com.example.demo.entity.KhachHang;
import com.example.demo.reponstory.KhachHangReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/view")
public class HomeController {

    @Autowired
    private KhachHangReponsitory khachHangReponsitory;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @GetMapping()
    public String index() {
        return "Index";
    }

    @GetMapping("/home")
    public String home() {
        return "Home";
    }

    @GetMapping("/ourStore")
    public String uorStore() {
        return "OurStore";
    }

    @GetMapping("/SearchOurStore")
    public String searchOurStore() {
        return "SearchOurStore";
    }

    @GetMapping("/header")
    public String header(Model model) {
        SecurityContext  securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) principal;
            model.addAttribute("userDetails", user.getAttribute("name") != null ? user.getAttribute("name") : user.getAttribute("login"));
        } else {
            KhachHang khachHang = khachHangReponsitory.findByEmail(customSuccessHandler.nameLogin());
            model.addAttribute("userDetails", khachHang.getTen());
        }
        return "layout/Header";
    }

    @GetMapping("/footer")
    public String footer() {
        return "layout/Footer";
    }

    @GetMapping("/detail")
    public String detail() {
        return "ProductDetails";
    }

    @GetMapping("/gioHang")
    public String gioHang() {
        return "Cart";
    }

    @GetMapping("/bill")
    public String bill() {
        return "bill";
    }


}
