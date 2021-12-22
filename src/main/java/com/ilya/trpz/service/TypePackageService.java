package com.ilya.trpz.service;

import com.ilya.trpz.model.TypePackage;
import com.ilya.trpz.repository.TypePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class TypePackageService {

    @Autowired
    TypePackageRepository typePackageRepository;

    public TypePackage findByTitle(String name){
        return typePackageRepository.findByTitle(name);
    }

    public List<TypePackage> findAll(){
        return typePackageRepository.findAll();
    }

    public boolean save(TypePackage typePackage){
        try {
            typePackageRepository.save(typePackage);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
