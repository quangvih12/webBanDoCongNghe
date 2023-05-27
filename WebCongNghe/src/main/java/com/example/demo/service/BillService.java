package com.example.demo.service;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.hoaDonChiTiet;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface BillService {

   List<HoaDon> getAll(Integer chucNang);

    ResponseEntity<List<hoaDonChiTiet>> getAllHDCT( Integer id);

    HashMap<String,Object> updateHoaDon(Integer id, HoaDon hoaDon);
}
