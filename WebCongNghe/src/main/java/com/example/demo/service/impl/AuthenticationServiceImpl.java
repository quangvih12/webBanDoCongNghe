package com.example.demo.service.impl;

import com.example.demo.request.AuthenticationRequest;
import com.example.demo.request.AuthenticationResponse;
import com.example.demo.request.RegisterRequest;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenType;
import com.example.demo.reponstory.KhachHangReponsitory;
import com.example.demo.reponstory.TokenRepository;
import com.example.demo.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final KhachHangReponsitory repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private static Integer currentLoginId;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var khacHang = KhachHang.builder()
                .ten(request.getFirstname())
                .tenDem(request.getLastname())
                .email(request.getEmail())
                .matKhau(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var saveKhachHang = repository.save(khacHang);
        var jwtToken = jwtService.generateToken(khacHang);
        saveUserToken(saveKhachHang, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpSession session) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            // Xử lý ngoại lệ đăng nhập không thành công
            return AuthenticationResponse.builder().error("Thông tin đăng nhập không chính xác").build();
        }

        var khachHang = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(khachHang);

        // lay ra id khach hang
        currentLoginId = khachHang.getId();

        revokeAllUserTokens(khachHang);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(KhachHang khacHang, String jwtToken) {
        var token = Token.builder()
                .khachHang(khacHang)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(KhachHang khacHang) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(khacHang.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public static Integer getCurrentLoginId() {
        return currentLoginId;
    }
}
