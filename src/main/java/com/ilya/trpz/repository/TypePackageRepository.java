package com.ilya.trpz.repository;

import com.ilya.trpz.model.TypePackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypePackageRepository extends JpaRepository<TypePackage, Long> {
    TypePackage findByTitle(String title);
}
