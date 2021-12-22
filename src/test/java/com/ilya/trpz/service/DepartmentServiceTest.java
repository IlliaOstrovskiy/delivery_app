package com.ilya.trpz.service;

import com.ilya.trpz.TrpzApplication;
import com.ilya.trpz.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrpzApplication.class)
public class DepartmentServiceTest {
    @Autowired
    DepartmentService  departmentService;

    @Test
    public void shouldSaveDepartment(){
        Department department = departmentService.save(new Department(1L, "City", 3L));
        assertNotNull(department);
    }

    @Test
    public void shouldReturnCorrectSizeOfAllDepartments(){
        departmentService.save(new Department(1L, "City1", 3L));
        departmentService.save(new Department(2L, "City2", 4L));
        assertEquals(2, departmentService.findAll().size());
    }

    @Test
    public void shouldFindByIdDepartment(){
        departmentService.save(new Department(1L, "City1", 3L));
        assertNotNull(departmentService.findById(1L));
    }


}