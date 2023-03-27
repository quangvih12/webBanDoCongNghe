package com.example.demo.service;

import com.example.demo.entity.GioHangChiTiet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartDetailService {
    ResponseEntity<List<GioHangChiTiet>> getAll();

    void createGioHangCT(Integer idSanPHamCT, Integer soLuong);

    ResponseEntity<HttpStatus> deleteGioHangCT(Integer id);

    ResponseEntity<HttpStatus> deleteAllGioHangCT();

    ResponseEntity<GioHangChiTiet> updateCongSoLuong(Integer id);

    ResponseEntity<GioHangChiTiet> updateTruSoLuong(Integer id);
}
