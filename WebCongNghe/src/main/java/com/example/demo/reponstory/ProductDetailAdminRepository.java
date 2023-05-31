package com.example.demo.reponstory;

import com.example.demo.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailAdminRepository extends JpaRepository<ChiTietSanPham, Integer> {
}
