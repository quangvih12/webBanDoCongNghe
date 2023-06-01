package com.example.demo.reponstory;

import com.example.demo.entity.NamSanXuat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NamSanXuatReponsitory extends JpaRepository<NamSanXuat,Integer> {
}
