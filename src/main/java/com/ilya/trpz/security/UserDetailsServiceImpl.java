package com.ilya.trpz.security;

import com.ilya.trpz.model.User;
import com.ilya.trpz.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    @Autowired
    private UserDetailsServiceImpl(UserService loginService) {
        this.userService = loginService;
    }


    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        if (user == null || !user.getEnabled()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
    }

}
