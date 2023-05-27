package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.hoaDonChiTiet;
import com.example.demo.reponstory.BillDetailReponsitory;
import com.example.demo.reponstory.BillReponsitory;
import com.example.demo.reponstory.CartDetailReponsitory;
import com.example.demo.reponstory.ProductReponstory;
import com.example.demo.service.BillDetailService;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    private BillDetailReponsitory hoaDonCtRespon;

    @Autowired
    private BillReponsitory hoaDonRespon;

    @Autowired
    private CartDetailReponsitory gioHangCTRespon;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private LoginGoogleServiceImpl loginGoogleService;

    @Autowired
    private ProductReponstory productReponstory;

    private DatetimeUtil datetimeUtil;

    private Set<Integer> numberSet = new HashSet<>();

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
    // luu idsp vao listIdSp
    public String saveCheckbox(Integer idSanPham) {
        // add tam thoi id san pham vao hashSet
        numberSet.add(idSanPham);
        for (int number : lsitIdSanPhamSession) {
            if (!numberSet.add(number)) {
                // Nếu phát hiện số trùng lặp, loại bỏ nó khỏi danh sách
                lsitIdSanPhamSession.remove((Integer) number);
            }
        }
        return "";
    }

    @Override
    public String removeCheckbox(Integer idSanPham) {
        // remove khoi hashSet
        numberSet.remove(idSanPham);
        return "";
    }

    @Override
    // test luu idsp
    public void get() {
        System.out.println("-------------------");
        for (Integer s : numberSet) {
            System.out.println(s);
        }
    }

    @Override
    // tao ra hoa don
    public HoaDon saveHoaDon(BigDecimal TongTienHoaDon, HoaDon _hoaDon) {
        int idKh;
        if (authenticationService.getCurrentLoginId() != null) {
            idKh = authenticationService.getCurrentLoginId();
        } else {
            idKh = loginGoogleService.getIdUser();
        }
        KhachHang kh = KhachHang.builder().id(idKh).build();
        HoaDon hoaDon = HoaDon.builder()
                .ngayThanhToan(datetimeUtil.getCurrentDate())
                .ghiChu(_hoaDon.getGhiChu())
                .diaChi(_hoaDon.getDiaChi())
                .ngayTao(datetimeUtil.getCurrentDate())
                .phuongThucTT(_hoaDon.getPhuongThucTT())
                .tongTien(TongTienHoaDon)
                .sdt(_hoaDon.getSdt())
                .tenNguoiNhan(_hoaDon.getTenNguoiNhan())
                .trangThaiTT(0)
                .khachHang(kh)
                .build();
        return this.hoaDonRespon.save(hoaDon);
    }


    @Override
    // ham save theo khach chon san pham trong gio hang
    public HashMap<String, Object> saveTheoID(BigDecimal TongTienHoaDon, HoaDon _hoaDon) {

        Integer idKh;
        if (authenticationService.getCurrentLoginId() != null) {
            idKh = authenticationService.getCurrentLoginId();
        } else {
            idKh = loginGoogleService.getIdUser();
        }
        if (idKh == null) {
            HashMap<String, Object> map = DataUltil.setData("error", "vui lòng đăng nhập");
            return map;
        }
        List<GioHangChiTiet> listGioHang = gioHangCTRespon.findAll();
        List<hoaDonChiTiet> listHoaDonCT = new ArrayList<>();


        HoaDon hoaDon = this.saveHoaDon(TongTienHoaDon, _hoaDon);
        hoaDon.setMa("HD" + hoaDon.getId());

        // lay id sp tu session roi save all
        for (Integer i : numberSet) {
            ChiTietSanPham ctSp = ChiTietSanPham.builder().id(i).build();
            GioHangChiTiet giohang = gioHangCTRespon.getChiTietSP(i, idKh);
            hoaDonChiTiet hd = new hoaDonChiTiet();
            hd.setChiTietSP(ctSp);
            hd.setHoaDon(hoaDon);
            hd.setDongia(giohang.getDonGia());
            hd.setSoLuong(giohang.getSoLuong());
            hd.setNgayTao(datetimeUtil.getCurrentDateAndTime());
            hd.setTrangthai(0);
            listHoaDonCT.add(hd);

            // update lai so luong ton trong kho
            Optional<ChiTietSanPham> chiTietSanPham = productReponstory.findById(i);
            if (chiTietSanPham.isPresent()) {
                ChiTietSanPham ct = chiTietSanPham.get();
                ct.setSoLuongTon(ct.getSoLuongTon() - giohang.getSoLuong());
                productReponstory.save(ct);
            }
        }

        hoaDonCtRespon.saveAll(listHoaDonCT);

        //xoa sau khi them vao hoa don chi tiet thanh cong
        for (GioHangChiTiet o : listGioHang) {
            for (Integer i : numberSet) {
                if (o.getChiTietSP().getId() == i) {
                    gioHangCTRespon.delete(o);
                }
            }
            // remove idsp khoi set
            numberSet.remove(o.getChiTietSP().getId());
        }
        HashMap<String, Object> map = DataUltil.setData("error", "thanh toán thành công");
        return map;
    }
}
