package com.example.demo.reponstory;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductReponstory extends JpaRepository<ChiTietSanPham, Integer> {

    @Query(value ="Select pt from ChiTietSanPham pt  where pt.sanPham.ten like :keyword or pt.sanPham.ma like :keyword")
    List<ChiTietSanPham> findAllBySanphamTen(String keyword);

    @Query("Select pt from ChiTietSanPham pt where pt.giaBan  BETWEEN :from AND :to")
    List<ChiTietSanPham> findByGiaBanBetween(BigDecimal from, BigDecimal to);

    @Query("Select pt from ChiTietSanPham pt where pt.dongsp.ten like  :nameDongSP")
    List<ChiTietSanPham> findByDongSp(String nameDongSP);

//    @Query("SELECT ghct FROM ChiTietSanPham ghct WHERE ghct.id =:idsp and ghct..khachHang.id=:idKh")
//    GioHangChiTiet getByIdSP(Integer idsp, Integer idKh);

}
