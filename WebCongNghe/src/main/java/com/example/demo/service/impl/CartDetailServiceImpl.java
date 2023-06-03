package com.example.demo.service.impl;

import com.example.demo.entity.Cart;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.KhachHang;
import com.example.demo.reponstory.CartDetailReponsitory;
import com.example.demo.reponstory.CartsReponstory;
import com.example.demo.reponstory.ProductReponstory;
import com.example.demo.service.CartDetailService;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private List<GioHangChiTiet> listGioHang = new ArrayList<>();

    @Override
    // find all theo id khach hang
    public List<?> getAll(HttpSession httpSession) {
        Integer idKh;
        if (authenticationService.getCurrentLoginId() != null) {
            idKh = authenticationService.getCurrentLoginId();
        } else {
            idKh = loginGoogleService.getIdUser();
        }
        if (idKh == null) {
            Cart cart = (Cart) httpSession.getAttribute("cart");
            if (cart != null) {
                listGioHang = cart.getItems();
            }
        } else {
            listGioHang = gioHangCTRespon.findAllByTen(idKh);
            Cart cart = (Cart) httpSession.getAttribute("cart");
            if (cart != null) {
//                KhachHang kh = KhachHang.builder().id(idKh).build();
//                GioHang gh = GioHang.builder().khachHang(kh).build();
//                List<GioHangChiTiet> liss = cart.getItems();
//                liss.forEach(o->o.setGioHang(gh));
                listGioHang.addAll(cart.getItems());
//                listGioHang.forEach(o->o.getGioHang().getId());
            }
        }

        return listGioHang;

    }

    public HashMap<String, Object> addCart(Integer id, Integer soLuong, HttpSession httpSession) {
        Integer idKh;
        if (authenticationService.getCurrentLoginId() != null) {
            idKh = authenticationService.getCurrentLoginId();
        } else {
            idKh = loginGoogleService.getIdUser();
        }
        if (idKh == null) {
            HashMap<String, Object> map = this.addCartSession(id, soLuong, httpSession);
            return map;
        }
        HashMap<String, Object> map = this.addCartDataBase(id, soLuong, idKh);
        return map;
    }

    public HashMap<String, Object> addCartDataBase(Integer id, Integer soLuong, Integer idKh) {
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
                GioHang gh = cartsReponstory.save(_gioHang);
                GioHangChiTiet gioHangChiTiet = GioHangChiTiet.builder()
                        .soLuong(soLuong)
                        .gioHang(gh)
                        .donGia(sanPham.getGiaBan())
                        .chiTietSP(sanPham)
                        .ngayTao(DatetimeUtil.getCurrentDate())
                        .build();
                gioHangCTRespon.save(gioHangChiTiet);
                HashMap<String, Object> map = DataUltil.setData("error", "thêm thành công");
                return map;
            }
        }
    }

    public HashMap<String, Object> addCartSession(Integer id, Integer soLuong, HttpSession httpSession) {
        // lấy ctsp từ repo
        Optional<ChiTietSanPham> chiTietSanPham = productReponstory.findById(id);
        // tạo ra giỏ hàng chi tiết
        GioHangChiTiet gioHang = GioHangChiTiet.builder().chiTietSP(chiTietSanPham.get()).soLuong(soLuong).ngayTao(DatetimeUtil.getCurrentDate()).build();
        //lấy gior hàng từ session
        Cart cartSesion = (Cart) httpSession.getAttribute("cart");
        // nếu chưa có giỏ hàng
        if (cartSesion == null) {
            Cart cart = new Cart();
            List<GioHangChiTiet> list = new ArrayList<>();
            list.add(gioHang);
            cart.setItems(list);
            httpSession.setAttribute("cart", cart);
//                list.forEach(item1 -> System.out.println(item1.getIdCtsp()));
        } else {
            // nếu có giỏ hàng
            Cart cart = (Cart) httpSession.getAttribute("cart");
            List<GioHangChiTiet> listItem = cart.getItems();
            // kieemr tra sản phẩm đã có trong giỏ hàng chưa
            // nếu có thì tăng số lwonjg lên 1
            for (GioHangChiTiet itemTmp : listItem) {
                if (itemTmp.getChiTietSP().getId().equals(id)) {
                    itemTmp.setSoLuong(gioHang.getSoLuong() + soLuong);
                    HashMap<String, Object> map = DataUltil.setData("error", "thêm thành công");
                    return map;
                }
            }
            // không có thì thêm sản phẩm vào
            listItem.add(gioHang);
//                listItem.forEach(item1 -> System.out.println(item1.getIdCtsp()));
        }
        HashMap<String, Object> map = DataUltil.setData("error", "thêm thành công");
        return map;
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
    public HashMap<String, Object> updateCongSoLuong(Integer id) {
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();

            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() + 1);
            gioHangCTRespon.save(_gioHangChiTiet);
            HashMap<String, Object> map = DataUltil.setData("warning", "ok");
            return map;
        } else {
            HashMap<String, Object> map = DataUltil.setData("warning", "lỗi");
            return map;
        }
    }

    @Override
    public HashMap<String, Object> updateTruSoLuong(Integer id) {
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();
            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() - 1);
            if (_gioHangChiTiet.getSoLuong() <= 0) {
                this.deleteGioHangCT(id);
                HashMap<String, Object> map = DataUltil.setData("warning", "sản phẩm không được nhỏ bằng 0");
                return map;
            } else {
                gioHangCTRespon.save(_gioHangChiTiet);
                HashMap<String, Object> map = DataUltil.setData("warning", "ok");
                return map;
            }

        } else {
            HashMap<String, Object> map = DataUltil.setData("warning", "lỗi");
            return map;
        }

    }

}
