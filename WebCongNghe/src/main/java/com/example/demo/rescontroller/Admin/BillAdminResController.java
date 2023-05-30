package com.example.demo.rescontroller.Admin;


import com.example.demo.entity.HoaDon;
import com.example.demo.service.impl.AdminServiceImpl.BillAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/BillAdmin")
public class BillAdminResController {
    @Autowired
    private BillAdminServiceImpl billAdminService;

    @GetMapping("/{trangThai}")
    public ResponseEntity<?> getAll(@PathVariable Integer trangThai) {
        return ResponseEntity.ok(billAdminService.getAll(trangThai));
    }

    @PutMapping("/xacNhanHoaDon/{id}")
    public ResponseEntity<?> updateHoaDon(@PathVariable("id") Integer id, @RequestBody HoaDon hoaDon) {
        HashMap<String, Object> map = billAdminService.updateHoaDon(id, hoaDon);
        return ResponseEntity.ok(map);
    }
}
