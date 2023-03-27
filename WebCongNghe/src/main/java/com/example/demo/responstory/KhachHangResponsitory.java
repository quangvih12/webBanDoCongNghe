package com.example.demo.responstory;

import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangResponsitory extends JpaRepository<KhachHang, Integer> {

    @Query("SELECT kh FROM KhachHang kh WHERE kh.email =:email AND kh.matKhau=:matKhau")
    Boolean findKhachHangByEmailAndMatKhau(String email, String matKhau);

    Optional<KhachHang> findByEmail(String email);

}
