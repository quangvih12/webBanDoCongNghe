package com.example.demo.rescontroller.Admin;


import com.example.demo.service.impl.AdminServiceImpl.ProductDetailAdminServiceImpl;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/api/productAdmin")
public class ProductDetailAdminResController {

    @Autowired
    private ProductDetailAdminServiceImpl productDetailAdminService;


    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "0") Integer size) {
        return ResponseEntity.ok(productDetailAdminService.getAll(page, size));
    }

    @PostMapping("/upload-ChiTietSp-data")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.productDetailAdminService.saveCustomersToDatabase(file);
        HashMap<String, Object> map = DataUltil.setData("error", " thêm sản phẩm thành công");
        return ResponseEntity.ok(map);
    }
}
