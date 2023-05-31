package com.example.demo.service;

import com.example.demo.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;

public interface ProductDetailAdminService {
    Page<ChiTietSanPham> getAll(Integer page, Integer size);
}
