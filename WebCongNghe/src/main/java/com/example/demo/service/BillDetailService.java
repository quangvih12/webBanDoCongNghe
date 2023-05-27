package com.example.demo.service;

import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.hoaDonChiTiet;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface BillDetailService {

    ResponseEntity<List<hoaDonChiTiet>> getAll();

    ResponseEntity<hoaDonChiTiet> getTutorialById(Integer id);

    String saveCheckbox(Integer idSanPham);

    String removeCheckbox(Integer idSanPham);

    HashMap<String, Object> createHoaDonCT(BigDecimal TongTienHoaDon, HoaDon _hoaDon);

    void get();

    HoaDon saveHoaDon( BigDecimal TongTienHoaDon,HoaDon _hoaDon);

    HashMap<String, Object> saveTheoID(BigDecimal TongTienHoaDon, HoaDon _hoaDon);

}
