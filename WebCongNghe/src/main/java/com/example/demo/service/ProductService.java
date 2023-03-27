package com.example.demo.service;

import com.example.demo.entity.ChiTietSanPham;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ResponseEntity<List<ChiTietSanPham>> getAll(Integer page, Integer size, String nameSort
    );

    ResponseEntity<List<ChiTietSanPham>> searchGiaTien(BigDecimal from, BigDecimal to);

    ResponseEntity<List<ChiTietSanPham>> searchDongSp(String name);

    ResponseEntity<ChiTietSanPham> getSanPhamById(Integer id);

    ResponseEntity<List<ChiTietSanPham>> searchSanPhamHeader(String keyword);
}
