package com.example.demo.service;

import com.example.demo.request.AuthenticationRequest;
import com.example.demo.request.AuthenticationResponse;
import com.example.demo.request.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request, HttpSession session, Model model);
}
