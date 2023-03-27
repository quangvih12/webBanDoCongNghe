package com.example.demo.responstory;

import com.example.demo.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartResponsitory extends JpaRepository<GioHang, Integer> {
 
}
