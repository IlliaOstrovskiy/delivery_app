package com.ilya.trpz.service;

import com.ilya.trpz.dto.SimpleUserDTO;
import com.ilya.trpz.dto.UserDTO;
import com.ilya.trpz.model.RoleType;
import com.ilya.trpz.model.User;
import com.ilya.trpz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService{
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean saveUser(UserDTO userDTO) {
        User userFromDb = userRepository.findByUsername(userDTO.getUsername());
        if (userFromDb != null) {
            return false;
        }
        userRepository.save(User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .enabled(true)
                .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                .role(RoleType.ROLE_USER)
                .phoneNumber(userDTO.getPhoneNumber())
                .build());
        return true;
    }

    public void editUser(SimpleUserDTO userDTO) {
        System.out.println(userDTO.toString());
        if (userDTO.getId() != null) {
            userRepository.save(User.builder()
                    .id(userDTO.getId())
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .password(userDTO.getPassword())
                    .role(RoleType.ROLE_USER).build());
        }
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findAll(pageable);
    }


    @Transactional
    public void disableStudentById(long studentId) {
        User user = userRepository.findById(studentId).get();
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


    public boolean saveCourierAsUser(User user) {
        User userCourier = userRepository.findByUsername(user.getUsername());
        if (userCourier != null) {
            return false;
        }
        userRepository.save(User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(true)
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .role(RoleType.ROLE_COURIER)
                .build());
        return true;
    }

    public void editCourier(User courier) {
        userRepository.save(User.builder()
                .id(courier.getId())
                .firstName(courier.getFirstName())
                .lastName(courier.getLastName())
                .email(courier.getEmail())
                .role(courier.getRole())
                .username(courier.getUsername())
                .enabled(courier.getEnabled())
                .password(courier.getPassword()).build());
    }

    public List<User> listUsersCourier(RoleType role){
        return userRepository.findUsersByRole(role);
    }


    public List<User> allUsers(){
        return userRepository.findAll();
    }
}