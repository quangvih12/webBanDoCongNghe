package com.example.demo.service.impl;

import com.example.demo.entity.KhachHang;
import com.example.demo.reponstory.KhachHangReponsitory;
import com.example.demo.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangReponsitory khachHangResponsitory;


    @Override
    public Optional<KhachHang> findByEmail(String email) {
        return khachHangResponsitory.findByEmail(email);
    }

    @Override
    public Boolean checkLogin(String email,String matKhau) {
        return khachHangResponsitory.findKhachHangByEmailAndMatKhau( email, matKhau);
    }



}
