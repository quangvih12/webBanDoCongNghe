package com.example.demo.service;

import com.example.demo.entity.GioHangChiTiet;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface CartDetailService {
    List<?> getAll(HttpSession httpSession);
    
    ResponseEntity<HttpStatus> deleteGioHangCT(Integer id);

    ResponseEntity<HttpStatus> deleteAllGioHangCT();

    HashMap<String, Object> updateCongSoLuong(Integer id);

    HashMap<String, Object> updateTruSoLuong(Integer id);
}
