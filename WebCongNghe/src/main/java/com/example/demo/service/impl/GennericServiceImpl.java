//package com.example.demo.service.impl;
//
//import com.example.demo.reponstory.GenericRepository;
//import com.example.demo.service.GenericSevice;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Optional;
//
//public class GennericServiceImpl<T, PK extends Serializable> implements GenericSevice<T, PK> {
//
//    @Autowired
//    private GenericRepository genericRepository;
//
//    private Class<T> entityClass;
//
//    @Override
//    public List<T> getAll() {
//        return genericRepository.findAll();
//    }
//
//    @Override
//    public Optional<T> getOne(PK id) {
//        Optional<T> en = genericRepository.findById(id);
//        return en;
//    }
//
//    @Override
//    public T add(T entity) {
//        genericRepository.save(entity);
//        return entity;
//    }
//
//    @Override
//    public T update(PK id) {
//        Optional<T> en = genericRepository.findById(id);
//        if(en.isPresent()){
//          return null;
//        }else{
//        return null;}
//    }
//}
