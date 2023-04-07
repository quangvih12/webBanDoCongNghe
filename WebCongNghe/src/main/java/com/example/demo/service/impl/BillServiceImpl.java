package com.example.demo.service.impl;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.hoaDonChiTiet;
import com.example.demo.reponstory.BillDetailReponsitory;
import com.example.demo.reponstory.BillReponsitory;
import com.example.demo.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillReponsitory hoaDonRespon;

    @Autowired
    private BillDetailReponsitory hoaDonCTRespon;

    private AuthenticationServiceImpl authenticationService;

    @Override
    // get all hoa Don theo id khach hang
    public ResponseEntity<List<HoaDon>> getAll() {
        try {
            List<HoaDon> HoaDonList = new ArrayList<>();
            int idKh = authenticationService.getCurrentLoginId();
            hoaDonRespon.findAllByTen(idKh).forEach(HoaDonList::add);
            if (HoaDonList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HoaDonList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    // khi click vao hoa don thi san phan se hien ra theo hoa don chi tiet
    public ResponseEntity<List<hoaDonChiTiet>> getAllHDCT(Integer id) {
        try {
            List<hoaDonChiTiet> HoaDonCTList = new ArrayList<>();
            hoaDonCTRespon.getAllHDCT(id).forEach(HoaDonCTList::add);
            if (HoaDonCTList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HoaDonCTList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // huy hoa don
    @Override
    public ResponseEntity<HoaDon> updateHoaDon(Integer id, HoaDon hoaDon) {
        Optional<HoaDon> hoaDonData = hoaDonRespon.findById(id);

        if (hoaDonData.isPresent()) {
            HoaDon _hoaDon = hoaDonData.get();
            _hoaDon.setTrangThaiTT(hoaDon.getTrangThaiTT());
           hoaDonRespon.save(_hoaDon);
            return new ResponseEntity<>(hoaDonRespon.save(_hoaDon), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
