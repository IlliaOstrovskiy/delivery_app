package com.ilya.trpz.service;

import com.ilya.trpz.TrpzApplication;
import com.ilya.trpz.model.Department;
import com.ilya.trpz.model.Package;
import com.ilya.trpz.model.StatusPackage;
import com.ilya.trpz.model.TypePackage;
import com.ilya.trpz.repository.DepartmentRepository;
import com.ilya.trpz.repository.PackageRepository;
import com.ilya.trpz.repository.TypePackageRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrpzApplication.class)
class PackageServiceTest {
    @Autowired
    PackageService packageService;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TypePackageService typePackageService;
    @Autowired
    TypePackageRepository typePackageRepository;

    @Test
    public void shouldSavePackage() {
        Department department = new Department(1L, "NEW", 87L);
        TypePackage typePackage = new TypePackage(1L, "NEW");
        departmentRepository.save(department);
        typePackageRepository.save(typePackage);
        Package pac = packageService.savePackage(Package.builder()
                .id(1L)
                .type(typePackage)
                .dateReceive(null)
                .dateSend(LocalDateTime.now())
                .status(StatusPackage.ACCEPTED)
                .price(10.0)
                .weight(10.0)
                .depart_receive(department)
                .depart_send(department)
                .build());
        assertThat(pac).isNotNull();
    }

    @Test
    public void shouldFindByIDPackage() {
        Department department = new Department(1L, "NEW", 87L);
        TypePackage typePackage = new TypePackage(1L, "NEW");
        departmentRepository.save(department);
        typePackageRepository.save(typePackage);
        packageService.savePackage(Package.builder()
                .id(1L)
                .type(typePackage)
                .dateReceive(null)
                .dateSend(LocalDateTime.now())
                .status(StatusPackage.ACCEPTED)
                .price(10.0)
                .weight(10.0)
                .depart_receive(department)
                .depart_send(department)
                .build());
        Package newPack = packageService.findById(1L);
        assertNotNull(newPack);
    }
}