package com.example.demo.rescontroller;


import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.service.impl.ProductSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductResController {
    @Autowired
    private ProductSeviceImpl productSevice;

    @GetMapping("/getAll")
    public ResponseEntity<List<ChiTietSanPham>> getAll(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer size,
                                                       @RequestParam(required = false) String nameSort
    ) {
        return productSevice.getAll(page, size, nameSort);
    }

    // tiem kiem theo khoang gia
    @GetMapping("/productSearchPrice")
    public ResponseEntity<List<ChiTietSanPham>> searchGiaTien(@RequestParam(required = false) BigDecimal from, @RequestParam(required = false) BigDecimal to) {
        return productSevice.searchGiaTien(from, to);
    }

    //tiem kiem theo dong san pham
    @GetMapping("/productDongSP")
    public ResponseEntity<List<ChiTietSanPham>> searchDongSp(@RequestParam(required = false) String name) {
        return productSevice.searchDongSp(name);
    }

    // detail
    @GetMapping("/{id}")
    public ResponseEntity<ChiTietSanPham> getSanPhamById(@PathVariable("id") Integer id) {
        return productSevice.getSanPhamById(id);
    }

    // tim kiem tren thanh header
    @GetMapping("/searchHeader")
    public ResponseEntity<List<ChiTietSanPham>> searchSanPhamHeader(@RequestParam(required = false) String keyword) {
        return productSevice.searchSanPhamHeader(keyword);
    }

}
