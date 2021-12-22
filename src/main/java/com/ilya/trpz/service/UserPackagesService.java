package com.ilya.trpz.service;

import com.ilya.trpz.dto.UserPackageDTO;
import com.ilya.trpz.model.Department;
import com.ilya.trpz.model.Package;
import com.ilya.trpz.model.StatusPackage;
import com.ilya.trpz.model.User;
import com.ilya.trpz.model.UserPackages;
import com.ilya.trpz.repository.PackageRepository;
import com.ilya.trpz.repository.UserPackagesRepository;
import com.ilya.trpz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserPackagesService {
    @Autowired
    UserPackagesRepository userPackagesRepository;
    @Autowired
    UserService userService;
    @Autowired
    PackageService packageService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    TypePackageService typePackageService;
    @Autowired
    PackageRepository packageRepository;

    public void saveUserPackages(UserPackageDTO newPackage) {
        Department depSend = departmentService.findByNumAndTown(newPackage.getNewPackage().getDepart_send().getDepart(), newPackage.getNewPackage().getDepart_send().getTown());
        Department depReceive = departmentService.findByNumAndTown(newPackage.getNewPackage().getDepart_receive().getDepart(), newPackage.getNewPackage().getDepart_receive().getTown());
        packageService.savePackage(Package.builder()
                .depart_send(depSend)
                .depart_receive(depReceive)
                .status(StatusPackage.ACCEPTED)
                .type(typePackageService.findByTitle(newPackage.getNewPackage().getType().getTitle()))
                .dateSend(LocalDateTime.now())
                .dateReceive(null)
                .title(newPackage.getNewPackage().getTitle())
                .weight(newPackage.getNewPackage().getWeight())
                .price(newPackage.getNewPackage().getPrice())
                .build());
        userPackagesRepository.save(UserPackages.builder()
                .newPackage(packageService.findById(packageService.findTop()))
                .sender(userService.findUserByUserName(newPackage.getSender().getUsername()))
                .recipient(userService.findUserByUserName(newPackage.getRecipient().getUsername()))
                .courier(userService.findUserByUserName(newPackage.getCourier().getUsername()))
                .build());
    }

    public void updateUserPackages(UserPackageDTO newPackage) {
        Department depSend = departmentService.findByNumAndTown(newPackage.getNewPackage().getDepart_send().getDepart(), newPackage.getNewPackage().getDepart_send().getTown());
        newPackage.getNewPackage().setDepart_send(depSend);
        Department depReceive = departmentService.findByNumAndTown(newPackage.getNewPackage().getDepart_receive().getDepart(), newPackage.getNewPackage().getDepart_receive().getTown());
        newPackage.getNewPackage().setDepart_receive(depReceive);
        newPackage.setCourier(userService.findUserByUserName(newPackage.getCourier().getUsername()));
        newPackage.setSender(userService.findUserByUserName(newPackage.getSender().getUsername()));
        newPackage.setRecipient(userService.findUserByUserName(newPackage.getRecipient().getUsername()));
        newPackage.getNewPackage().setStatus(newPackage.getNewPackage().getStatus());
        newPackage.getNewPackage().setType(typePackageService.findByTitle(newPackage.getNewPackage().getType().getTitle()));
        if(newPackage.getNewPackage().getStatus().equals(StatusPackage.RECEIVED)){
            newPackage.getNewPackage().setDateReceive(LocalDateTime.now());
        }else newPackage.getNewPackage().setDateReceive(null);
        packageRepository.save(Package.builder()
                .id(newPackage.getNewPackage().getId())
                .title(newPackage.getNewPackage().getTitle())
                .weight(newPackage.getNewPackage().getWeight())
                .price(newPackage.getNewPackage().getPrice())
                .status(newPackage.getNewPackage().getStatus())
                .dateSend(newPackage.getNewPackage().getDateSend())
                .dateReceive(newPackage.getNewPackage().getDateReceive())
                .type(newPackage.getNewPackage().getType())
                .depart_receive(newPackage.getNewPackage().getDepart_receive())
                .depart_send(newPackage.getNewPackage().getDepart_send())
                .build());
        userPackagesRepository.save(UserPackages.builder()
                .id(newPackage.getId())
                .newPackage(packageService.findById(newPackage.getNewPackage().getId()))
                .sender(userService.findUserByUserName(newPackage.getSender().getUsername()))
                .recipient(userService.findUserByUserName(newPackage.getRecipient().getUsername()))
                .courier(userService.findUserByUserName(newPackage.getCourier().getUsername()))
                .build());
    }
    public UserPackages findById(long id) {
        return userPackagesRepository.findByNewPackageId(id);
    }

    public List<Package> findAllSendByMe(User user){

        List<UserPackages> userPackages = userPackagesRepository.findUserPackagesBySender(user);
        List<Package> list = new ArrayList<>();
        for(UserPackages userPackages1 : userPackages){
           list.add(userPackages1.getNewPackage());
        }
        return list;
    }
    public List<Package> findAllRecipientByMeWithoutReceived(User user){
        List<UserPackages> userPackages = userPackagesRepository.findUserPackagesByRecipient(user);
        List<Package> list = new ArrayList<>();
        for(UserPackages userPackages1 : userPackages){
            if(!userPackages1.getNewPackage().getStatus().equals(StatusPackage.RECEIVED))
            list.add(userPackages1.getNewPackage());
        }
        return list;
    }
    public List<Package> findAllRecipientByMeWithReceived(User user){
        List<UserPackages> userPackages = userPackagesRepository.findUserPackagesByRecipient(user);
        List<Package> list = new ArrayList<>();
        for(UserPackages userPackages1 : userPackages){
            if(userPackages1.getNewPackage().getStatus().equals(StatusPackage.RECEIVED))
                list.add(userPackages1.getNewPackage());
        }
        return list;
    }
    public void deleteById(Long id) {
        userPackagesRepository.deleteById(id);
    }


}
