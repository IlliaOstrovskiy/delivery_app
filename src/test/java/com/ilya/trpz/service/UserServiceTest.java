package com.ilya.trpz.service;

import com.ilya.trpz.TrpzApplication;
import com.ilya.trpz.dto.UserDTO;
import com.ilya.trpz.model.*;
import com.ilya.trpz.model.Package;

import com.ilya.trpz.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrpzApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldReturnTrueWhenSaveUser() {
        UserDTO userDTO = new UserDTO(77L, "Name", "Surname",
                "gmail@gmail.com", "Username", "12345", "12345", "7777777777");
        boolean isAdd = userService.saveUser(userDTO);
        assertTrue(isAdd);
    }

    @Test
    public void shouldFindById(){
        UserDTO userDTO = new UserDTO(77L, "Name", "Surname",
                "gmail@gmail.com", "Username", "12345", "12345", "7777777777");
        userService.saveUser(userDTO);
        User user = userService.findById(1L);
        assertNotNull(user);
    }
    @Test
    public void shouldDeleteUser() {
        UserDTO userDTO = new UserDTO(77L, "Name", "Surname",
                "gmail@gmail.com", "Username", "12345", "12345", "7777777777");
        userService.saveUser(userDTO);
        userService.deleteUser(1L);
        assertEquals(0, userRepository.findAll().size());
    }

}