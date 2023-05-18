package com.example.demo.rescontroller;


import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/loginGoogle")
public class LoginGoogleResController {

    @GetMapping("/gg")
    public Map<String, Object> user(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        if (oAuth2AuthenticationToken != null && oAuth2AuthenticationToken.isAuthenticated()) {
            return oAuth2AuthenticationToken.getPrincipal().getAttributes();
        } else {
            // Xử lý khi người dùng chưa xác thực hoặc xác thực không thành công
            // Ví dụ: chuyển hướng đến trang đăng nhập
            return new HashMap<>(); // Trả về một giá trị rỗng hoặc thông tin mặc định
        }
    }


}
