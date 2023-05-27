package com.example.demo.reponstory;


import com.example.demo.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillReponsitory extends JpaRepository<HoaDon, Integer> {

    @Query("Select pt from HoaDon pt where pt.khachHang.id=:id")
     List<HoaDon> findAllByTen(Integer id);

    @Query("select  pt from HoaDon pt where pt.trangThaiTT=:id and pt.khachHang.id=:idKH")
    List<HoaDon> findAllHistory(Integer id, Integer idKH);
}
