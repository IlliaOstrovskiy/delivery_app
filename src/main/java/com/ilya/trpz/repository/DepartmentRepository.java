package com.ilya.trpz.repository;

import com.ilya.trpz.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDepartAndTown(Long depart, String town);
    @Query("select distinct e.town from Department e")
    List<String> listUnique();
}
