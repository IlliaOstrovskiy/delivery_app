package com.ilya.trpz.web;


import com.ilya.trpz.model.Package;
import com.ilya.trpz.model.StatusPackage;
import com.ilya.trpz.model.User;
import com.ilya.trpz.service.PackageService;
import com.ilya.trpz.service.UserPackagesService;
import com.ilya.trpz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PackageService packageService;
    @Autowired
    UserPackagesService userPackagesService;

    @GetMapping("/my_account")
    public String myCabinet(Principal principal) {
        User user = userService.findUserByUserName(principal.getName());
        return "redirect:my_account/" + user.getUsername();
    }

    @GetMapping("/my_account/{username}")
    public String userPage(@PathVariable(value = "username") String username, Model model) {
        /*User user = userService.findUserByUserName(username);
        model.addAttribute("user", user);
        List<Package> mySentPackages = packageService.findPackagesBySenderId(user.getId());
        model.addAttribute("sentPackages", mySentPackages);
        List<Package> myRecipientPackages = packageService.findPackagesByRecipientId(user.getId());
        List<Package> myPackWithoutReceived = new ArrayList<>();
        for(Package pac : myRecipientPackages){
            if(!pac.getStatus().equals(StatusPackage.RECEIVED)){
                myPackWithoutReceived.add(pac);
            }
        }
        List<Package> myArchiveReceivedPackages = packageService.findPackagesByRecipientIdAndStatusReceived(user.getId(),
                StatusPackage.RECEIVED);
        model.addAttribute("sentPackages", mySentPackages);
        model.addAttribute("myRecipientPackages", myPackWithoutReceived);
        model.addAttribute("myArchiveReceivedPackages", myArchiveReceivedPackages);*/
        User user = userService.findUserByUserName(username);
        model.addAttribute("user", user);
        List<Package> mySentPackages = userPackagesService.findAllSendByMe(user);
        List<Package> myRecipientPackages = userPackagesService.findAllRecipientByMeWithoutReceived(user);
        List<Package> myArchiveReceivedPackages = userPackagesService.findAllRecipientByMeWithReceived(user);
        model.addAttribute("sentPackages", mySentPackages);
        model.addAttribute("myRecipientPackages", myRecipientPackages);
        model.addAttribute("myArchiveReceivedPackages", myArchiveReceivedPackages);
        return "my_account";
    }


}
