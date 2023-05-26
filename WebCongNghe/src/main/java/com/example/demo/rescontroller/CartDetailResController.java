package com.example.demo.rescontroller;


import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.service.impl.CartDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/gioHang")
public class CartDetailResController {
    @Autowired
    private CartDetailServiceImpl cartDetailService;

    // get all
    @GetMapping("/getAll")
    public ResponseEntity<List<GioHangChiTiet>> getAll() {
        return cartDetailService.getAll();
    }

    // them san pham vao gio hang
    @PostMapping("/create")
    public void createGioHangCT(@RequestParam Integer idSanPHamCT,
                                @RequestParam Integer soLuong) {
        cartDetailService.createGioHangCT(idSanPHamCT, soLuong);
    }

    // xoa khoi gio hang theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGioHangCT(@PathVariable("id") Integer id) {
        return cartDetailService.deleteGioHangCT(id);
    }

    // xoa tat ca khoi gio hang
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteAllGioHangCT() {
        return cartDetailService.deleteAllGioHangCT();
    }

    @PutMapping("/gioHangs/{id}")
    public ResponseEntity<GioHangChiTiet> updateCongSoLuong(@PathVariable("id") Integer id, @RequestBody GioHangChiTiet gioHangChiTiet) {
        return cartDetailService.updateCongSoLuong(id);
    }

    @PutMapping("/gioHang/{id}")
    public ResponseEntity<GioHangChiTiet> updateTruSoLuong(@PathVariable("id") Integer id) {
        return cartDetailService.updateTruSoLuong(id);

    }

    @PostMapping("/add")
    public ResponseEntity<?> addCart(@RequestParam Integer idSanPHamCT,
                                     @RequestParam Integer soLuong, Principal principal) {
        HashMap<String, Object> map = cartDetailService.addCart(idSanPHamCT, soLuong, principal);
        return ResponseEntity.ok(map);
    }

}
