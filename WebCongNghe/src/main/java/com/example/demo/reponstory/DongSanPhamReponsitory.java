package com.example.demo.reponstory;

import com.example.demo.entity.DongSp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongSanPhamReponsitory extends JpaRepository<DongSp, Integer> {
}
