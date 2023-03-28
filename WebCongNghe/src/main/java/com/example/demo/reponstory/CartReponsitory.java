package com.example.demo.reponstory;

import com.example.demo.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartReponsitory extends JpaRepository<GioHang, Integer> {
 
}
