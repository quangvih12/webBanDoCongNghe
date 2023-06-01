package com.example.demo.reponstory;

import com.example.demo.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamReponsitory extends JpaRepository<SanPham, Integer> {
}
