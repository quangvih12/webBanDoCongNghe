package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.hoaDonChiTiet;
import com.example.demo.responstory.BillDetailResponsitory;
import com.example.demo.responstory.BillResponsitory;
import com.example.demo.responstory.CartDetailResponsitory;
import com.example.demo.service.BillDetailService;
import com.example.demo.util.DatetimeUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    private BillDetailResponsitory hoaDonCtRespon;

    @Autowired
    private BillResponsitory hoaDonRespon;

    @Autowired
    private CartDetailResponsitory gioHangCTRespon;

    private AuthenticationServiceImpl authenticationService;

    private DatetimeUtil datetimeUtil;

    // dung list de lay id san pham tu gio hang roi save vao hoa don chi tiet
    private List<Integer> lsitIdSanPhamSession = new ArrayList<>();

    @Override
    // get all hoa don chi tiet
    public ResponseEntity<List<hoaDonChiTiet>> getAll() {
        try {
            List<hoaDonChiTiet> hoaDonCTList = new ArrayList<>();
            hoaDonCtRespon.findAll().forEach(hoaDonCTList::add);
            if (hoaDonCTList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(hoaDonCTList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    // tim kiem theo id
    public ResponseEntity<hoaDonChiTiet> getTutorialById(Integer id) {
        Optional<hoaDonChiTiet> hoaDonData = hoaDonCtRespon.findById(id);
        if (hoaDonData.isPresent()) {
            return new ResponseEntity<>(hoaDonData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    // luu idsp vao session
    public String saveCheckbox(Integer idSanPham, HttpSession session) {
        session.setAttribute("idSanPham", idSanPham);

        // add tam thoi id san pham vao list
        lsitIdSanPhamSession.add(idSanPham);
        System.out.println(idSanPham);

        return "";
    }

    @Override
    // test luu session
    public void get() {
        for (Integer i : lsitIdSanPhamSession) {
            System.out.println(i);
        }
    }

    @Override
    // thanh toan
    public ResponseEntity<hoaDonChiTiet> createHoaDonCT(BigDecimal TongTienHoaDon, HoaDon _hoaDon) {
        try {
            List<GioHangChiTiet> listGioHang = gioHangCTRespon.findAll();
            List<hoaDonChiTiet> listHoaDonCT = new ArrayList<>();

            if (lsitIdSanPhamSession.isEmpty()) {
                this.saveAll(TongTienHoaDon, _hoaDon, listGioHang, listHoaDonCT);
            } else {
                this.saveTheoID(TongTienHoaDon, _hoaDon, listGioHang, listHoaDonCT);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    // tao ra hoa don
    public HoaDon saveHoaDon(BigDecimal TongTienHoaDon, HoaDon _hoaDon) {
        int idKh = authenticationService.getCurrentLoginId();
        KhachHang kh = KhachHang.builder().id(idKh).build();
        HoaDon hoaDon = HoaDon.builder()
                .ngayThanhToan(datetimeUtil.getCurrentDateAndTime())
                .ghiChu(_hoaDon.getGhiChu())
                .diaChi(_hoaDon.getDiaChi())
                .ngayTao(datetimeUtil.getCurrentDateAndTime())
                .phuongThucTT(_hoaDon.getPhuongThucTT())
                .tongTien(TongTienHoaDon)
                .sdt(_hoaDon.getSdt())
                .tenNguoiNhan(_hoaDon.getTenNguoiNhan())
                .trangThaiTT(1)
                .khachHang(kh)
                .build();
        return this.hoaDonRespon.save(hoaDon);
    }

    @Override
    // ham save all san pham
    public void saveAll(BigDecimal TongTienHoaDon, HoaDon _hoaDon, List<GioHangChiTiet> listGioHang, List<hoaDonChiTiet> listHoaDonCT) {
        HoaDon hoaDon = this.saveHoaDon(TongTienHoaDon, _hoaDon);
        hoaDon.setMa("HD" + hoaDon.getId());
//          lay ra id trong gio hang roi save all lai
        for (GioHangChiTiet o : listGioHang) {

            ChiTietSanPham ctSp = ChiTietSanPham.builder().id(o.getChiTietSP().getId()).build();
            GioHangChiTiet giohang = gioHangCTRespon.getBySoLuong(o.getChiTietSP().getId());
            hoaDonChiTiet hd = new hoaDonChiTiet();
            hd.setChiTietSP(ctSp);
            hd.setHoaDon(hoaDon);
            hd.setDongia(o.getDonGia());
            hd.setSoLuong(giohang.getSoLuong());
            hd.setNgayTao(datetimeUtil.getCurrentDateAndTime());
            hd.setTrangthai(0);
            listHoaDonCT.add(hd);

        }
        hoaDonCtRespon.saveAll(listHoaDonCT);
        gioHangCTRespon.deleteAll(listGioHang);

    }

    @Override
    // ham save theo khach chon san pham trong gio hang
    public void saveTheoID(BigDecimal TongTienHoaDon, HoaDon _hoaDon, List<GioHangChiTiet> listGioHang, List<hoaDonChiTiet> listHoaDonCT) {
        HoaDon hoaDon = this.saveHoaDon(TongTienHoaDon, _hoaDon);
        hoaDon.setMa("HD" + hoaDon.getId());
        // lay id sp tu session roi save all
        for (Integer i : lsitIdSanPhamSession) {
            System.out.println(i);
            ChiTietSanPham ctSp = ChiTietSanPham.builder().id(i).build();
            GioHangChiTiet giohang = gioHangCTRespon.getBySoLuong(i);
            hoaDonChiTiet hd = new hoaDonChiTiet();
            hd.setChiTietSP(ctSp);
            hd.setHoaDon(hoaDon);
            hd.setDongia(giohang.getDonGia());
            hd.setSoLuong(giohang.getSoLuong());
            hd.setNgayTao(datetimeUtil.getCurrentDateAndTime());
            hd.setTrangthai(0);
            listHoaDonCT.add(hd);
            System.out.println(giohang.getDonGia());
        }

        hoaDonCtRespon.saveAll(listHoaDonCT);

        //xoa sau khi them vao hoa don chi tiet thanh cong
        for (GioHangChiTiet o : listGioHang) {
            for (Integer i : lsitIdSanPhamSession) {
                if (o.getChiTietSP().getId() == i) {
                    gioHangCTRespon.delete(o);
                }
            }
        }
    }
}
