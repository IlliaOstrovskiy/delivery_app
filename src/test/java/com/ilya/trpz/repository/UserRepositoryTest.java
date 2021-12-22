package com.ilya.trpz.repository;

import com.ilya.trpz.dto.UserDTO;
import com.ilya.trpz.model.RoleType;
import com.ilya.trpz.model.User;
import com.ilya.trpz.security.UserDetailsServiceImpl;
import com.ilya.trpz.service.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserDetailsServiceImpl us;
    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private MockMvc mockMvc;

    @Test
    public void shouldReturnNotNullWhenFindByUsername() {
        User user = new User(77L, "Name", "Surname",
                "gmail@gmail.com", "Username", "12345", "7777777777", true, RoleType.ROLE_USER);
        Mockito.doReturn(user)
                .when(userRepository)
                .findByUsername("Username");
        Assertions.assertNotNull(userRepository.findByUsername("Username"));
    }

    @Test
    public void shouldReturnExWhenLoadByNoExistsUsername() {
        Mockito.doReturn(new User())
                .when(userRepository)
                .findByUsername("NoFound");
        Throwable exception = assertThrows(UsernameNotFoundException.class,
                () -> us.loadUserByUsername("Username"));
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void shouldReturnTrueWhenAddNewUser() {
        UserDTO userDTO = new UserDTO(77L, "Name", "Surname",
                "gmail@gmail.com", "Username", "12345", "12345", "7777777777");
        boolean isAdd = userService.saveUser(userDTO);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("Username");
        assertTrue(isAdd);
    }

    @Test
    public void shouldReturnFalseWhenAddUserWithSampleUsername() {
        UserDTO userDTO = new UserDTO(77L, "Name", "Surname",
                "gmail@gmail.com", "Username", "12345", "12345", "7777777777");
        Mockito.doReturn(new User())
                .when(userRepository)
                .findByUsername("Username");

        boolean isAdd = userService.saveUser(userDTO);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("Username");
        assertFalse(isAdd);
    }

    @Test
    public void shouldReturnCorrectSizeOfUserList() {
        User user1 = User.builder()
                .id(77L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .username("Username")
                .email("email1")
                .enabled(true)
                .password("12341")
                .build();
        User user2 = User.builder()
                .id(88L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .username("Username1")
                .email("email1")
                .enabled(true)
                .password("12341")
                .build();
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Mockito.doReturn(userList)
                .when(userRepository)
                .findAll();
        assertThat(userService.allUsers().size()).isEqualTo(2);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldReturnUserWhenLoadByExistsId() {
         Optional<User> optionalUser = Optional.of(User.builder()
                .id(88L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .username("Username1")
                .email("email1")
                .enabled(true)
                .password("12341")
                .build());
        Mockito.doReturn(optionalUser)
                .when(userRepository)
                .findById(88L);
        Optional<User> optional = userRepository.findById(88L);
        assertNotNull(optional);
    }

    @Test
    public void shouldReturnTrueWheDeleteIsSuccess() {
        User user = new User();
        user.setFirstName("Test Name");
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        boolean isDelete = userService.deleteUser(user.getId());
        verify(userRepository).deleteById(user.getId());
        assertTrue(isDelete);
    }

    @Test
    public void shouldReturnFalseWhenDeleteIsNotSuccess() {
        User user = new User();
        user.setId(89L);
        user.setFirstName("Test Name");
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        boolean isDelete = userService.deleteUser(user.getId());
        Mockito.verify(userRepository, Mockito.times(0)).deleteById(89L);
        assertFalse(isDelete);
    }
}