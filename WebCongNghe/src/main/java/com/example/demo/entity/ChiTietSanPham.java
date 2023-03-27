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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;
import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Entity
@Table(name = "Chi_TietSP")
public class ChiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdSP")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "Id_Nsx")
    private NamSanXuat nSX;

    @ManyToOne
    @JoinColumn(name = "Id_Mau_Sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "Id_DongSP")
    private DongSp dongsp;

    @ManyToOne
    @JoinColumn(name = "id_Thuong_hieu")
    private thuongHieu thuongHieu;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "NamBH")
    private int namBH;

    @Nationalized
    @Column(name = "Mo_Ta")
    private String moTa;

    @Column(name = "hinh_anh")
    private String hinhAnh;

    @Column(name = "So_Luong_Ton")
    private int soLuongTon;

    @Column(name = "Gia_Nhap")
    private BigDecimal giaNhap;

    @Column(name = "Gia_Ban")
    private BigDecimal giaBan;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @JsonIgnore
    @OneToMany(mappedBy = "chiTietSP", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    List<hoaDonChiTiet> listt = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chiTietSP", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    List<GioHangChiTiet> listtt = new ArrayList<>();


}
