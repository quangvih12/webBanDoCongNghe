package com.example.demo.service;

import com.example.demo.entity.KhachHang;

import java.util.Optional;

public interface KhachHangService {

     KhachHang findByEmail(String email);

    Boolean checkLogin(String email,String matKhau);

    KhachHang save(KhachHang khachHang);
}
