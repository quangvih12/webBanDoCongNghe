package com.example.demo.rescontroller;


import com.example.demo.entity.HoaDon;
import com.example.demo.entity.hoaDonChiTiet;
import com.example.demo.service.impl.BillDetailServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/hoaDon")
public class BillDetailResController {

    @Autowired
    private BillDetailServiceImpl billService;


    // get all hoa don chi tiet
    @GetMapping()
    public ResponseEntity<List<hoaDonChiTiet>> getAll() {
        return billService.getAll();

    }

    // tim kiem theo id
    @GetMapping("/{id}")
    public ResponseEntity<hoaDonChiTiet> getBillById(@PathVariable("id") Integer id) {
        return billService.getTutorialById(id);
    }

    // luu idsp vao hashSet
    @PostMapping("/save-checkbox")
    public String saveCheckbox(@RequestParam("idSanPham") Integer idSanPham) {
        return billService.saveCheckbox(idSanPham);
    }

    // reomve idsp vao hashSet
    @PostMapping("/remove-checkbox")
    public String removeCheckbox(@RequestParam("idSanPham") Integer idSanPham) {
        return billService.removeCheckbox(idSanPham);
    }

    // test luu session
    @GetMapping("/sa")
    public void get() {
        billService.get();
    }

    // thanh toan
    @PostMapping("/createhoaDon")
    public ResponseEntity<?> createHoaDonCT(@RequestParam(required = false) BigDecimal TongTienHoaDon,
                                            @RequestBody HoaDon _hoaDon) {
        HashMap<String, Object> map = billService.saveTheoID(TongTienHoaDon, _hoaDon);
        return ResponseEntity.ok(map);
    }


}
