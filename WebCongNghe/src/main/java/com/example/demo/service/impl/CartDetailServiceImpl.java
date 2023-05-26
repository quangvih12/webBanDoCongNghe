package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.KhachHang;
import com.example.demo.reponstory.CartDetailReponsitory;
import com.example.demo.reponstory.CartsReponstory;
import com.example.demo.reponstory.ProductReponstory;
import com.example.demo.service.CartDetailService;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailReponsitory gioHangCTRespon;


    @Autowired
    private CartsReponstory cartsReponstory;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private LoginGoogleServiceImpl loginGoogleService;

    @Autowired
    private ProductReponstory productReponstory;


    @Override
    // find all theo id khach hang
    public ResponseEntity<List<GioHangChiTiet>> getAll() {
        try {
            List<GioHangChiTiet> listGioHang = new ArrayList<>();
            int idKh;
            if (authenticationService.getCurrentLoginId() != null) {
                idKh = authenticationService.getCurrentLoginId();
            } else {
                idKh = loginGoogleService.getIdUser();
            }
            gioHangCTRespon.findAllByTen(idKh).forEach(listGioHang::add);
            if (listGioHang.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(listGioHang, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    //them vao gio hang
    public void createGioHangCT(Integer idSanPHamCT, Integer soLuong) {
        if (soLuong <= 0) {
            System.out.println("soLuong ko <= 0");
        } else {
            int idKh = authenticationService.getCurrentLoginId();
            KhachHang kh = KhachHang.builder().id(idKh).build();
            GioHang _gioHang = GioHang.builder().khachHang(kh).build();
//            GioHang gh = gioHangRespon.save(_gioHang);
//            gioHangCTRespon.createGioHangCT(idSanPHamCT, gh.getId(), soLuong);
        }

    }

    public HashMap<String, Object> addCart(Integer id, Integer soLuong, Principal principal) {
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
        ChiTietSanPham sanPham = productReponstory.getById(id);
        if (soLuong > sanPham.getSoLuongTon()) {
            HashMap<String, Object> map = DataUltil.setData("error", "số lượng sản phẩm không đủ");
            return map;
        } else {
            KhachHang kh = KhachHang.builder().id(idKh).build();
            GioHang _gioHang = GioHang.builder().khachHang(kh).build();
            Optional<GioHangChiTiet> optional = this.getById(kh.getId(), sanPham.getId());
            if (optional.isPresent()) {
                GioHangChiTiet gioHangChiTiet = optional.get();
                if (soLuong + gioHangChiTiet.getSoLuong() > sanPham.getSoLuongTon()) {
                    HashMap<String, Object> map = DataUltil.setData("error", "số lượng tối đa chỉ còn: " + sanPham.getSoLuongTon());
                    return map;
                } else {
                    gioHangChiTiet.setSoLuong(soLuong + gioHangChiTiet.getSoLuong());
                    gioHangCTRespon.save(gioHangChiTiet);
                    HashMap<String, Object> map = DataUltil.setData("error", "thêm thành công");
                    return map;
                }
            } else {
                System.out.println("e");
                GioHang gh = cartsReponstory.save(_gioHang);
                GioHangChiTiet gioHangChiTiet = GioHangChiTiet.builder()
                        .soLuong(soLuong)
                        .gioHang(gh)
                        .chiTietSP(sanPham)
                        .build();
                gioHangCTRespon.save(gioHangChiTiet);
                HashMap<String, Object> map = DataUltil.setData("error", "thêm thành công");
                return map;
            }
        }
    }


    public Optional<GioHangChiTiet> getById(Integer id, Integer idsp) {
        return gioHangCTRespon.findById(id, idsp);
    }


    @Override
    // xoa khoi gio hang theo id
    public ResponseEntity<HttpStatus> deleteGioHangCT(Integer id) {
        try {
            gioHangCTRespon.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    // xoa tat ca khoi gio hang
    public ResponseEntity<HttpStatus> deleteAllGioHangCT() {
        try {
            gioHangCTRespon.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<GioHangChiTiet> updateCongSoLuong(Integer id) {
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();
            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() + 1);
            return new ResponseEntity<>(gioHangCTRespon.save(_gioHangChiTiet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<GioHangChiTiet> updateTruSoLuong(Integer id) {
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();
            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() - 1);
            return new ResponseEntity<>(gioHangCTRespon.save(_gioHangChiTiet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
