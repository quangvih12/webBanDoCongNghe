package com.example.demo.reponstory;


import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.SanPham;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailReponsitory extends JpaRepository<GioHangChiTiet, Integer> {

    @Transactional
    @Modifying
    @Query(value = "IF EXISTS (SELECT chi_tietsp.id FROM  gio_hang_chi_tiet  join chi_tietsp ON gio_hang_chi_tiet.idchi_tietsp = chi_tietsp.id \n" +
            "\t\t\t\t\t\t\t\t\t\t\tWHERE  gio_hang_chi_tiet.idchi_tietsp =:idSanPHamCT)\n" +
            "\t\t\t\tBEGIN\n" +
            "\t\t\t\t\tUPDATE gio_hang_chi_tiet SET so_luong = so_luong+:soLuong WHERE gio_hang_chi_tiet.idchi_tietsp =:idSanPHamCT\n" +
            "\t\t\t\t\n" +
            "\t\t\t\tEND\n" +
            "\t\t\tELSE\t\n" +
            "\t\t\t\tBEGIN\n" +
            "\t\t\t\t\tINSERT INTO gio_hang_chi_tiet(don_gia,idchi_tietsp,so_luong,ngay_tao,id_gio_hang )\n" +
            "\t\t\t\t\t\tVALUES ((SELECT chi_tietsp.gia_ban FROM chi_tietsp WHERE id =:idSanPHamCT),:idSanPHamCT,:soLuong,getdate(),:idGioHang)\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t\tEND", nativeQuery = true)
    void createGioHangCT(Integer idSanPHamCT, Integer idGioHang, Integer soLuong);

    @Query("SELECT ghct FROM GioHangChiTiet ghct WHERE ghct.chiTietSP.id =:idsp")
    GioHangChiTiet getBySoLuong(Integer idsp);

    @Query("DELETE FROM GioHangChiTiet ghct WHERE ghct.chiTietSP.id =:idsp")
    void deleteGhById(Integer idsp);

    @Query("Select pt from GioHangChiTiet pt where pt.gioHang.khachHang.id=:id")
    List<GioHangChiTiet> findAllByTen(Integer id);


    @Query("Select pt from GioHangChiTiet pt where pt.gioHang.khachHang.id=:id and pt.chiTietSP.id=:idsp")
    Optional<GioHangChiTiet> findById(Integer id, Integer idsp);
}
