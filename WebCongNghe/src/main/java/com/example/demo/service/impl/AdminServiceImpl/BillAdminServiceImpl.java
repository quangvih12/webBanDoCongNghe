package com.example.demo.service.impl.AdminServiceImpl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.entity.hoaDonChiTiet;
import com.example.demo.reponstory.BillDetailReponsitory;
import com.example.demo.reponstory.BillReponsitory;
import com.example.demo.reponstory.KhachHangReponsitory;
import com.example.demo.reponstory.ProductReponstory;
import com.example.demo.service.BillService;
import com.example.demo.service.impl.AuthenticationServiceImpl;
import com.example.demo.service.impl.KhachHangServiceImpl;
import com.example.demo.service.impl.LoginGoogleServiceImpl;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BillAdminServiceImpl implements BillService {

    @Autowired
    private BillReponsitory hoaDonRespon;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private LoginGoogleServiceImpl loginGoogleService;

    @Autowired
    private KhachHangReponsitory khachHangReponsitory;

    @Autowired
    private BillDetailReponsitory hoaDonCTRespon;

    @Autowired
    private ProductReponstory productReponstory;

    @Override
    public List<HoaDon> getAll(Integer chucNang) {
        int idKh;
        if (authenticationService.getCurrentLoginId() != null) {
            idKh = authenticationService.getCurrentLoginId();
        } else {
            idKh = loginGoogleService.getIdUser();
        }
        KhachHang khachHang = khachHangReponsitory.findById(idKh).get();
        if (khachHang.getRole().equals(Role.ADMIN)) {
            System.out.println("ok");
            return hoaDonRespon.findAllHistoryAdmin(chucNang);
        } else {
            System.out.println("no");
            List<HoaDon> list = new ArrayList<>();
            return list;
        }

    }

    @Override
    public ResponseEntity<List<hoaDonChiTiet>> getAllHDCT(Integer id) {
        return null;
    }

    @Override
    public HashMap<String, Object> updateHoaDon(Integer id, HoaDon hoaDon) {
        Optional<HoaDon> hoaDonData = hoaDonRespon.findById(id);
        if (hoaDonData.isPresent()) {
            HoaDon _hoaDon = hoaDonData.get();
            _hoaDon.setTrangThaiTT(hoaDon.getTrangThaiTT());
            HoaDon hd = hoaDonRespon.save(_hoaDon);

            hoaDonChiTiet hoaDonChiTiet = hoaDonCTRespon.getByIdSP(hd.getId()).get();

            Optional<ChiTietSanPham> chiTietSanPham = productReponstory.findById(hoaDonChiTiet.getChiTietSP().getId());
            if (chiTietSanPham.isPresent()) {
                ChiTietSanPham ct = chiTietSanPham.get();
                ct.setSoLuongTon(ct.getSoLuongTon() + hoaDonChiTiet.getSoLuong());
                productReponstory.save(ct);
            }
            HashMap<String, Object> map = DataUltil.setData("warning", "Xác Nhận đơn hàng: " + _hoaDon.getMa());
            return map;
        } else {
            HashMap<String, Object> map = DataUltil.setData("warning", "không tìm thấy hóa đơn bạn muốn hủy");
            return map;
        }
    }
}
