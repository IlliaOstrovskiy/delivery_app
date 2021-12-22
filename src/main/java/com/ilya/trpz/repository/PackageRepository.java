package com.ilya.trpz.repository;

import com.ilya.trpz.model.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    @Query("SELECT c FROM Package c WHERE c.title LIKE %?1%")
    Page<Package> findAll(String keyword, Pageable pageable);
    @Query("SELECT MAX(c.id) FROM Package c")
    Long maxId();
    void deleteById(Long id);
}
