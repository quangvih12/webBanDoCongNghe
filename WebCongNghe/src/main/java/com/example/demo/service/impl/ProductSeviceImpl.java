package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.reponstory.ProductReponstory;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSeviceImpl implements ProductService {

    @Autowired
    private ProductReponstory sanPhamResponstory;

    @Override
    public ResponseEntity<List<ChiTietSanPham>> getAll(Integer page, Integer size, String nameSort) {
        try {
            //tao 1 doi tuong ChiTietSanPham
            List<ChiTietSanPham> sanPhamList = new ArrayList<>();

            // neu = null het thi get all
            if (page == null && size == null && nameSort == null) {
                sanPhamResponstory.findAll().forEach(sanPhamList::add);
            }
            // nameSort la: tang dan && giam dan
            else if (page != null && size != null && nameSort == null) {
                Pageable pageable = PageRequest.of(page, size);
                sanPhamResponstory.findAll(pageable).forEach(sanPhamList::add);
            }
            // neu page && size == null && nameSort != null thi sort ko phan trang
            else if (page == null && size == null && nameSort != null) {
                // sort
                Sort sort = (nameSort == null || nameSort.equals("asc")) ? Sort.by(Sort.Direction.ASC, "giaBan") : Sort.by(Sort.Direction.DESC, "giaBan");
                sanPhamResponstory.findAll(sort).forEach(sanPhamList::add);
            }
            // else thi sort  phan trang
            else {
                Sort sort = (nameSort == null || nameSort.equals("asc")) ? Sort.by(Sort.Direction.ASC, "giaBan") : Sort.by(Sort.Direction.DESC, "giaBan");
                // page phan trnang
                Pageable pageable = PageRequest.of(page, size, sort);
                sanPhamResponstory.findAll(pageable).forEach(sanPhamList::add);
            }
            // kiểm tran SanPhamList có phần tử hay không
            // nếu SanPhamList rỗng thì trả về mã trạng thái 'NO_CONTENT' đã sử lý thành công nhưng ko có sanPham trả về
            // nếu SanPhamList không rỗng thì trả về mã trạng thái 'OK' đã sử lý thành công  vả có sanPham trả về
            if (sanPhamList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(sanPhamList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    // tiem kiem theo khoang gia
    public ResponseEntity<List<ChiTietSanPham>> searchGiaTien(BigDecimal from, BigDecimal to) {
        try {
            List<ChiTietSanPham> listSanPhamCT = new ArrayList<>();
            if (from == null || to == null) {
                sanPhamResponstory.findAll().forEach(listSanPhamCT::add);
            } else {
                sanPhamResponstory.findByGiaBanBetween(from, to).forEach(listSanPhamCT::add);
            }
            if (listSanPhamCT.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<>(listSanPhamCT, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ChiTietSanPham>> searchDongSp(String name) {
        try {
            List<ChiTietSanPham> listSanPhamCT = new ArrayList<>();
            if (name == null) {
                sanPhamResponstory.findAll().forEach(listSanPhamCT::add);
            } else {
                sanPhamResponstory.findByDongSp("%" + name + "%").forEach(listSanPhamCT::add);
            }
            if (listSanPhamCT.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<>(listSanPhamCT, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    // detail
    public ResponseEntity<ChiTietSanPham> getSanPhamById(Integer id) {
        Optional<ChiTietSanPham> sanPhamData = sanPhamResponstory.findById(id);

        //    kiểm tra xem giá trị trong Optional có tồn tại hay không.
        if (sanPhamData.isPresent()) {
            return new ResponseEntity<>(sanPhamData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(sanPhamData.get(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    // tim kiem tren thanh header
    public ResponseEntity<List<ChiTietSanPham>> searchSanPhamHeader(String keyword) {
        try {
            List<ChiTietSanPham> listSanPham = new ArrayList<>();
            if (keyword == null) {
                sanPhamResponstory.findAll().forEach(listSanPham::add);
            } else {
                sanPhamResponstory.findAllBySanphamTen("%" + keyword + "%").forEach(listSanPham::add);
            }

            if (listSanPham.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(listSanPham, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
