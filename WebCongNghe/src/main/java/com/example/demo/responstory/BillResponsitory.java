package com.example.demo.responstory;


import com.example.demo.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillResponsitory extends JpaRepository<HoaDon, Integer> {

    @Query("Select pt from HoaDon pt where pt.khachHang.id=:id")
    public List<HoaDon> findAllByTen(Integer id);
}
