package com.example.demo.reponstory;

import com.example.demo.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacReponsitory extends JpaRepository<MauSac,Integer> {
}
