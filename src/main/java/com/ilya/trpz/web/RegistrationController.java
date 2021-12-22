package com.ilya.trpz.web;

import com.ilya.trpz.dto.UserDTO;
import com.ilya.trpz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
@Slf4j
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView addUser(@ModelAttribute("userForm") @Valid UserDTO userForm, BindingResult result, ModelAndView modelAndView) {
        log.info("trying to register new user");
        if (result.hasErrors()) {
            log.error("Invalid data");
            modelAndView.addObject("failureMessage", "signup.label.error");
        } else {
            if (userService.findUserByEmail(userForm.getEmail()) != null) {
                log.error("This email already exist");
                modelAndView.addObject("failureMessage", "signup.label.emailRegistered");
                modelAndView.setViewName("registration");
            }else if(userService.findUserByUserName(userForm.getUsername()) != null){
                log.error("This username already exist");
                modelAndView.addObject("failureMessage", "signup.label.alreadyRegistered");
                modelAndView.setViewName("registration");
            }else {
                userService.saveUser(userForm);
                modelAndView.setViewName("redirect:/registration?success");
            }
        }
        return modelAndView;
    }
}
