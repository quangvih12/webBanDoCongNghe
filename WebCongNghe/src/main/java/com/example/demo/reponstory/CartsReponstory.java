package com.example.demo.reponstory;

import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartsReponstory extends JpaRepository<GioHang, Integer> {

    @Query("Select pt from GioHang pt where pt.khachHang.id=:id")
     List<GioHang> findAllByIdKhachHang(Integer id);
}
