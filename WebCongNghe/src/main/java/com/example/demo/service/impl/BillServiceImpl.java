package com.example.demo.service.impl;

import com.example.demo.controller.HomeController;
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

    @Autowired
    private LoginGoogleServiceImpl loginGoogleService;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    private final Integer CHOXACNHAN = 0;
    private final Integer DANGGIAO = 1;
    private final Integer DAGIAO = 2;
    private final Integer DAHUY = -1;
    private final Integer DOITRA = 3;


    @Override
    // get all hoa Don theo id khach hang
    public List<HoaDon> getAll(Integer chucNang) {
        int idKh;
        if (authenticationService.getCurrentLoginId() != null) {
            idKh = authenticationService.getCurrentLoginId();
        } else {
            idKh = loginGoogleService.getIdUser();
        }
        return hoaDonRespon.findAllHistory(chucNang, idKh);
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
