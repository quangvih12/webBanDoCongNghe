package com.example.demo.service;

import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.hoaDonChiTiet;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface BillDetailService {

    ResponseEntity<List<hoaDonChiTiet>> getAll();

    ResponseEntity<hoaDonChiTiet> getTutorialById(Integer id);

    String saveCheckbox(Integer idSanPham, HttpSession session);

    String removeCheckbox(Integer idSanPham, HttpSession session);

    ResponseEntity<hoaDonChiTiet> createHoaDonCT(BigDecimal TongTienHoaDon, HoaDon _hoaDon);

    void get();

    HoaDon saveHoaDon(BigDecimal TongTienHoaDon, HoaDon _hoaDon);

    void saveAll(BigDecimal TongTienHoaDon, HoaDon _hoaDon, List<GioHangChiTiet> listGioHang, List<hoaDonChiTiet> listHoaDonCT);

    void saveTheoID(BigDecimal TongTienHoaDon, HoaDon _hoaDon, List<GioHangChiTiet> listGioHang, List<hoaDonChiTiet> listHoaDonCT);

}
