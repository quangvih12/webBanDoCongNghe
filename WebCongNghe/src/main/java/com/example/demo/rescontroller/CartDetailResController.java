package com.example.demo.rescontroller;


import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.service.impl.CartDetailServiceImpl;
import jakarta.servlet.http.HttpSession;
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

import java.util.HashMap;


@RestController
@RequestMapping("/api/gioHang")
public class CartDetailResController {
    @Autowired
    private CartDetailServiceImpl cartDetailService;

    // get all
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(HttpSession httpSession) {
        return ResponseEntity.ok(cartDetailService.getAll(httpSession));
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
    public ResponseEntity<?> updateCongSoLuong(@PathVariable("id") Integer id, @RequestBody GioHangChiTiet gioHangChiTiet) {
        HashMap<String, Object> map = cartDetailService.updateCongSoLuong(id);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/gioHang/{id}")
    public ResponseEntity<?> updateTruSoLuong(@PathVariable("id") Integer id) {
        HashMap<String, Object> map = cartDetailService.updateTruSoLuong(id);
        return ResponseEntity.ok(map);

    }

    @PostMapping("/add")
    public ResponseEntity<?> addCart(@RequestParam Integer idSanPHamCT,
                                     @RequestParam Integer soLuong, HttpSession httpSession) {
        HashMap<String, Object> map = cartDetailService.addCart(idSanPHamCT, soLuong, httpSession);
        return ResponseEntity.ok(map);
    }

}
