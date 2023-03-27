/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.entity;

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
@Table(name = "Nhan_Vien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Ma")
    private String ma;

    @Nationalized
    @Column(name = "Ten")
    private String ten;

    @Nationalized
    @Column(name = "Ten_Dem")
    private String tenDem;

    @Nationalized
    @Column(name = "Ho")
    private String ho;

    @Nationalized
    @Column(name = "Gioi_Tinh")
    private String gioiTinh;

    @Column(name = "Ngay_Sinh")
    private String ngaySinh;

    @Nationalized
    @Column(name = "Dia_Chi")
    private String diaChi;

    @Column(name = "Sdt")
    private String sdt;

    @Column(name = "Mat_Khau")
    private String matKhau;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Id_GuiBC")
    private String idGuiBC;

    @Column(name = "Trang_Thai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "IdCH")
    private CuaHang cuaHang;

    @ManyToOne
    @JoinColumn(name = "IdCV")
    private ChucVu chucVu;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HoaDon> listt = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "idNV", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<GioHang> lists = new ArrayList<>();
}
