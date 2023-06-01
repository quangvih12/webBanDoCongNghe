package com.example.demo.reponstory;

import com.example.demo.entity.thuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuReponsitory extends JpaRepository<thuongHieu,Integer> {
}
