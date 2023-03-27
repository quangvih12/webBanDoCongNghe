/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;
import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Entity
@Table(name = "Hoa_Don")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdKH")
    private KhachHang khachHang;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "Ngay_Tao")
    private String ngayTao;

    @Column(name = "Ngay_Thanh_Toan")
    private String ngayThanhToan;

    @Column(name = "Ngay_Ship")
    private String ngayShip;

    @Column(name = "Ngay_Nhan")
    private String ngayNhan;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Nationalized
    @Column(name = "Ten_Nguoi_Nhan")
    private String tenNguoiNhan;

    @Nationalized
    @Column(name = "Dia_Chi")
    private String diaChi;

    @Column(name = "Sdt")
    private String sdt;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @ManyToOne
    @JoinColumn(name = "IdNV")
    private NhanVien nhanVien;

    @Column(name = "TG_Tao")
    private String tgTao;

    @Column(name = "Tong_Tien")
    private BigDecimal tongTien;

    @Column(name = "Tien_Tra_Lai")
    private BigDecimal tienTraLai;

    @Column(name = "Trang_Thai_TT") // -1: Da huy, 0: Cho thanh toan, 1: Da thanh toan
    private Integer trangThaiTT;

    @ManyToOne
    @JoinColumn(name = "Phuong_Thuc_TT")
    private PhuongThucTT phuongThucTT;

    @ManyToOne
    @JoinColumn(name = "Hinh_Thuc_GH")
    private HinhThucGH HinhThucGH;

    @Nationalized
    @Column(name = "Ghi_Chu")
    private String ghiChu;

    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon", fetch = FetchType.EAGER)
    private List<hoaDonChiTiet> lstHDCT = new ArrayList<>();

}
