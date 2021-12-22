package com.ilya.trpz.service;

import com.ilya.trpz.model.Department;
import com.ilya.trpz.model.Package;
import com.ilya.trpz.model.StatusPackage;
import com.ilya.trpz.repository.DepartmentRepository;
import com.ilya.trpz.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PackageService {
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;
    @Autowired
    TypePackageService typePackageService;

    public Page<Package> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.packageRepository.findAll(pageable);
    }

    public Page<Package> listAll(int pageNum, String sortField, String sortDir, String keyword) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum-1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );
        if(keyword != null){
            return packageRepository.findAll(keyword, pageable);
        }
        pageable = PageRequest.of(pageNum - 1, pageSize);
        return this.packageRepository.findAll(pageable);
    }

    public Long findTop(){
        return packageRepository.maxId();
    }

    public Package findById(Long id) {
        return packageRepository.findById(id).get();
    }

    public Package savePackage(Package newPackage) {
        Department depSend = departmentService.findByNumAndTown(newPackage.getDepart_send().getDepart(), newPackage.getDepart_send().getTown());
        newPackage.setDepart_send(depSend);
        Department depReceive = departmentService.findByNumAndTown(newPackage.getDepart_receive().getDepart(), newPackage.getDepart_receive().getTown());
        newPackage.setDepart_receive(depReceive);
        newPackage.setStatus(StatusPackage.ACCEPTED);
        newPackage.setType(typePackageService.findByTitle(newPackage.getType().getTitle()));
        return packageRepository.save(Package.builder()
                .title(newPackage.getTitle())
                .weight(newPackage.getWeight())
                .price(newPackage.getPrice())
                .status(newPackage.getStatus())
                .dateSend(LocalDateTime.now())
                .dateReceive(null)
                .type(newPackage.getType())
                .depart_receive(newPackage.getDepart_receive())
                .depart_send(newPackage.getDepart_send())
                .build());
    }

    public void deleteById(Long id){
        packageRepository.deleteById(id);
    }
}
