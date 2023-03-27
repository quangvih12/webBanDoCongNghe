package com.example.demo.service.impl;

import com.example.demo.request.AuthenticationRequest;
import com.example.demo.request.AuthenticationResponse;
import com.example.demo.request.RegisterRequest;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenType;
import com.example.demo.responstory.KhachHangResponsitory;
import com.example.demo.responstory.TokenRepository;
import com.example.demo.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final KhachHangResponsitory repository;
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var khachHang = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(khachHang);

        // lay ra id khach hang
        currentLoginId = khachHang.getId();

        // luu ten ng dung vao session
        session.setAttribute("ten", khachHang.getTen());

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
