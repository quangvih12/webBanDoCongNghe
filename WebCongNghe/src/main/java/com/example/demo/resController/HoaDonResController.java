package com.example.demo.resController;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.hoaDonChiTiet;
import com.example.demo.service.impl.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HoaDonResController {

    @Autowired
    private BillServiceImpl billService;

    // get all hoa Don
    @GetMapping("/hoaDonD")
    public ResponseEntity<List<HoaDon>> getAll() {
        return billService.getAll();
    }

    // khi click vao hoa don thi san phan se hien ra theo hoa don chi tiet
    @GetMapping("/hoaDonD/{id}")
    public ResponseEntity<List<hoaDonChiTiet>> getAllHDCT(@PathVariable Integer id) {
        return billService.getAllHDCT(id);

    }

    @PutMapping("/hoaDonD/{id}")
    public ResponseEntity<HoaDon> updateHoaDon(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        return billService.updateHoaDon(id, hoaDon);
    }

}