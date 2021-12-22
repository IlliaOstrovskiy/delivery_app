package com.ilya.trpz.web;

import com.ilya.trpz.model.User;
import com.ilya.trpz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class MainController {

    @Autowired
    UserService userService;

    /*@GetMapping("/")
    public ModelAndView currentUser(ModelAndView modelAndView) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        System.out.println(username);
        User user = userService.findUserByUserName(username);
        if (user != null) {
            Double balance = user.getBalance();
            modelAndView.addObject("balance", balance);
            modelAndView.setViewName("index");
            return modelAndView;
        }
        return modelAndView;
    }*/
}
