package com.example.demo.service.impl.AdminServiceImpl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Role;
import com.example.demo.reponstory.KhachHangReponsitory;
import com.example.demo.reponstory.ProductDetailAdminRepository;
import com.example.demo.service.ProductDetailAdminService;
import com.example.demo.service.impl.AuthenticationServiceImpl;
import com.example.demo.service.impl.LoginGoogleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class ProductDetailAdminServiceImpl implements ProductDetailAdminService {
    @Autowired
    private ProductDetailAdminRepository productDetailAdminRepository;

    @Autowired
    private KhachHangReponsitory khachHangReponsitory;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private LoginGoogleServiceImpl loginGoogleService;
    @Autowired
    private ExcelUploadServiceImpl excelUploadService;

    @Override
    public Page<ChiTietSanPham> getAll(Integer page, Integer size) {
        int idKh;
        if (authenticationService.getCurrentLoginId() != null) {
            idKh = authenticationService.getCurrentLoginId();
        } else {
            idKh = loginGoogleService.getIdUser();
        }
        KhachHang khachHang = khachHangReponsitory.findById(idKh).get();
        if (khachHang.getRole().equals(Role.ADMIN)) {
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(page, size, sort);
            return productDetailAdminRepository.findAll(pageable);
        } else {
            List<ChiTietSanPham> emptyList = Collections.emptyList();
            Page<ChiTietSanPham> emptyPage = new PageImpl<>(emptyList, PageRequest.of(page, size), 0);
            return emptyPage;
        }

    }

    public void saveCustomersToDatabase(MultipartFile file) {
        if (ExcelUploadServiceImpl.isValidExcelFile(file)) {
            try {
                List<ChiTietSanPham> customers = excelUploadService.getCustomersDataFromExcel(file.getInputStream());
                customers.stream().forEach(chiTietSanPham -> {
                    chiTietSanPham.setMa("CTSP" + chiTietSanPham.getId());
                });
                this.productDetailAdminRepository.saveAll(customers);

            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }
}
