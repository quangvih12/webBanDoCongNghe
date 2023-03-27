package com.example.demo.service;

import com.example.demo.request.AuthenticationRequest;
import com.example.demo.request.AuthenticationResponse;
import com.example.demo.request.RegisterRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request, HttpSession session);
}
