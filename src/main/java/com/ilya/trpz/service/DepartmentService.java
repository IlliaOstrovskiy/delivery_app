package com.ilya.trpz.service;

import com.ilya.trpz.model.Department;
import com.ilya.trpz.model.User;
import com.ilya.trpz.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> findAll(){
        return departmentRepository.findAll();
    }

    public Department findByNumAndTown(Long num, String town){
        return departmentRepository.findByDepartAndTown(num, town);
    }

    public List<String> uniqueDep(){
        return departmentRepository.listUnique();
    }

    public Page<Department> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.departmentRepository.findAll(pageable);
    }

    public Department findById(long id) {
        return departmentRepository.findById(id).get();
    }

    public Department save(Department newDepart) {
        return departmentRepository.save(Department.builder()
                .id(newDepart.getId())
        .depart(newDepart.getDepart())
        .town(newDepart.getTown())
        .build());
    }

    public void deleteDepart(Long department) {
        departmentRepository.deleteById(department);
    }
}
