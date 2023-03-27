package com.example.demo.service;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.hoaDonChiTiet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillService {

    ResponseEntity<List<HoaDon>> getAll();

    ResponseEntity<List<hoaDonChiTiet>> getAllHDCT( Integer id);

    ResponseEntity<HoaDon> updateHoaDon(Integer id, HoaDon hoaDon);
}
