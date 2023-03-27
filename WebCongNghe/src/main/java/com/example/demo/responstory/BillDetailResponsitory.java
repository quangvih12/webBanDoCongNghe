package com.example.demo.responstory;


import com.example.demo.entity.hoaDonChiTiet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailResponsitory extends JpaRepository<hoaDonChiTiet, Integer> {

    @Transactional
    @Modifying
    @Query(value = " \t  INSERT INTO [dbo].[hoa_don_chi_tiet]\n" +
            "           ([don_gia]\n" +
            "           ,[so_luong]\n" +
            "           ,[trang_thai]\n" +
            "           ,[id_chi_tietsp]\n" +
            "           ,[id_hoa_don]\n" +
            "           ,[ngay_tao])\n" +
            "     VALUES\n" +
            "           ((SELECT chi_tietsp.gia_ban FROM chi_tietsp WHERE id =:idSp),\n" +
            "\t\t   :soLuong,\n" +
            "\t\t   0,\n" +
            "\t\t   (select ct.idchi_tietsp from gio_hang_chi_tiet ct WHERE ct.idchi_tietsp = :idSp), \n" +
            "\t\t  :idHd,\n" +
            "\t\t   getdate()  )", nativeQuery = true)
    void SaveHoaDonCT(Integer idSp, Integer soLuong, Integer idHd);


    @Query("SELECT hdct FROM hoaDonChiTiet hdct WHERE hdct.hoaDon.id = :id")
    List<hoaDonChiTiet> getAllHDCT(Integer id);
}
