package com.example.demo.rescontroller.Admin;


import com.example.demo.service.impl.AdminServiceImpl.ProductDetailAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
