package com.example.demo.controller;

import com.example.demo.service.impl.LogoutServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class LoginController {

    private final LogoutServiceImpl logoutService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("errorMessage", "Invalid username or password");
        return "Login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return "redirect:/login";
    }


}
