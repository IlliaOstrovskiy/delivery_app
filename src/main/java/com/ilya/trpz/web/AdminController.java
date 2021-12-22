package com.ilya.trpz.web;

import com.ilya.trpz.dto.PackageDTO;
import com.ilya.trpz.dto.SimpleUserDTO;
import com.ilya.trpz.dto.UserDTO;


import com.ilya.trpz.dto.UserPackageDTO;
import com.ilya.trpz.model.*;


import com.ilya.trpz.model.Package;
import com.ilya.trpz.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    PackageService packageService;

    @Autowired
    TypePackageService typePackageService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    UserPackagesService userPackagesService;

    @GetMapping("/user_list")
    public String userList(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("user_list/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<User> page = userService.findPaginated(pageNo, pageSize);
        List<User> listUsers = page.getContent();
        List<User> listOnlyUsers = new ArrayList<>();

        for (User user : listUsers) {
            if (user.getRole().name().equals("ROLE_USER")) {
                listOnlyUsers.add(user);
            }
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listOnlyUsers);
        return "users";
    }

    @GetMapping("/disableUser/{userid}")
    public String disableUser(@PathVariable(value = "userid") long userid, Model model) {
        userService.disableStudentById(userid);
        return "redirect:/user_list";
    }

    @GetMapping("delete_user/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/user_list";
    }

    @PostMapping("/update_user")
    public String saveUser(@ModelAttribute("user") @Valid SimpleUserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Errors");
            System.out.println(bindingResult.getAllErrors());
            return "/user";
        }
        userService.editUser(userDTO);
        return "redirect:/user_list";
    }

    @GetMapping("/edit_user/{id}")
    public String showFormForUpdateUser(@PathVariable(value = "id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/couriers")
    public String courierList(Model model) {
        return findPaginatedCouriers(1, model);
    }

    @GetMapping("courier_list/page/{pageNo}")
    public String findPaginatedCouriers(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<User> page = userService.findPaginated(pageNo, pageSize);
        List<User> listUsers = page.getContent();
        List<User> listOnlyCouriers = new ArrayList<>();
        for (User user : listUsers) {
            if (user.getRole().name().equals("ROLE_COURIER")) {
                listOnlyCouriers.add(user);
            }
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCouriers", listOnlyCouriers);
        return "couriers";
    }

    @GetMapping("/edit_courier/{id}")
    public String showFormForUpdateCourier(@PathVariable(value = "id") long id, Model model) {
        User courier = userService.findById(id);
        model.addAttribute("courier", courier);
        return "courier";
    }

    @PostMapping("/update_courier")
    public String editCourier(@ModelAttribute("courier") User courier) {
        userService.editCourier(courier);
        return "redirect:/couriers";
    }

    @GetMapping("/delete_courier/{id}")
    public String deleteCourier(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/couriers";
    }

    @GetMapping("/add_courier")
    public String courier(Model model) {
        model.addAttribute("courier", new User());
        return "add_courier";
    }

    @PostMapping("/add_courier")
    public String saveNewCourier(@ModelAttribute("courier") User user) {
        userService.saveCourierAsUser(user);
        return "redirect:/couriers";
    }

   /* @GetMapping("packages/page/{pageNo}")
    public String findPaginatedPackages(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<Package> page = packageService.findPaginated(pageNo, pageSize);
        List<Package> packageList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("packageList", packageList);
        return "packages";
    }*/


    @GetMapping("/packages")
    public String packageList(Model model) {
        String keyword = null;
        return viewPage(model, 1, "id", "asc", keyword);
    }

  /*  @GetMapping("packages/page/{pageNo}")
    public String findPaginatedPackages(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<Package> page = packageService.findPaginated(pageNo, pageSize);
        List<Package> packageList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("packageList", packageList);
        return "packages";
    }*/


    @RequestMapping("/packages/page/{pageNum}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir,
                           @Param("keyword") String keyword){

        Page<Package> page = packageService.listAll(pageNum, sortField, sortDir, keyword);
        List<Package> packageList = page.getContent();
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("packageList", packageList);
        return "packages";
    }


    @GetMapping("/package_info/{id}")
    public String showAdditionalInfo(@PathVariable(value = "id") long id, Model model) {
        UserPackages packages = userPackagesService.findById(id);
        List<Department> departments = departmentService.findAll();
        List<String> uniqDep = departmentService.uniqueDep();
        model.addAttribute("typePackagesList", typePackageService.findAll());
        model.addAttribute("departmentDepList", uniqDep);
        model.addAttribute("departmentList", departments);
        model.addAttribute("courierList", userService.listUsersCourier(RoleType.ROLE_COURIER));
        model.addAttribute("userList", userService.listUsersCourier(RoleType.ROLE_USER));
        model.addAttribute("statusTypes", Arrays.asList(StatusPackage.values()));
        model.addAttribute("package", packages);
        return "package_info";
    }

    @PostMapping("/package_info")
    public String editPackage(@ModelAttribute("package") @Valid UserPackageDTO newPackage) {
        userPackagesService.updateUserPackages(newPackage);
        return "redirect:/packages";
    }

    @GetMapping("/delete_package/{id}")
    public String deltePac(@PathVariable(value = "id") long id){
        UserPackages userPackages = userPackagesService.findById(id);
        userPackagesService.deleteById(userPackages.getId());
        packageService.deleteById(id);
        return "redirect:/packages";
    }


    @GetMapping("/add_package")
    public String newPackage(Model model) {
        model.addAttribute("newPackage", new UserPackages());
        model.addAttribute("typePackagesList", typePackageService.findAll());
        List<User> couriers = userService.listUsersCourier(RoleType.ROLE_COURIER);
        List<User> users = userService.listUsersCourier(RoleType.ROLE_USER);
        List<Department> departments = departmentService.findAll();
        List<String> uniqDep = departmentService.uniqueDep();
        model.addAttribute("departmentDepList", uniqDep);
        model.addAttribute("departmentList", departments);
        model.addAttribute("courierList", couriers);
        model.addAttribute("userList", users);
        return "add_package";
    }

    @PostMapping("/add_package")
    public String saveNewPackage(@ModelAttribute("newPackage") @Valid UserPackageDTO newPackage, BindingResult bindingResult, Model model) {
        model.addAttribute("typePackagesList", typePackageService.findAll());
        List<User> couriers = userService.listUsersCourier(RoleType.ROLE_COURIER);
        List<User> users = userService.listUsersCourier(RoleType.ROLE_USER);
        List<Department> departments = departmentService.findAll();
        List<String> uniqDep = departmentService.uniqueDep();
        model.addAttribute("departmentDepList", uniqDep);
        model.addAttribute("departmentList", departments);
        model.addAttribute("courierList", couriers);
        model.addAttribute("userList", users);
        if (bindingResult.hasErrors()) {
            return "/add_package";
        }
        userPackagesService.saveUserPackages(newPackage);
        return "redirect:/packages";
    }


    @GetMapping("/departments")
    public String departmentsList(Model model) {
        return findPaginatedDepartments(1, model);
    }

    @GetMapping("departments/page/{pageNo}")
    public String findPaginatedDepartments(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<Department> page = departmentService.findPaginated(pageNo, pageSize);
        List<Department> listDepart = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("departmentList", listDepart);
        return "departments";
    }

    @GetMapping("/edit_depart/{id}")
    public String editDepart(@PathVariable(value = "id") long id, Model model) {
        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "department";
    }

    @PostMapping("/update_depart")
    public String editDepartm(@ModelAttribute("department") Department newDepart) {
        departmentService.save(newDepart);
        return "redirect:/departments";
    }

    @GetMapping("/add_department")
    public String department(Model model) {
        model.addAttribute("newDepartment", new Department());
        return "add_department";
    }

    @PostMapping("/add_department")
    public ModelAndView saveNewDepartment(@ModelAttribute("newDepartment") Department department, BindingResult bindingResult, ModelAndView modelAndView) {
        try {
            departmentService.save(department);
            modelAndView.setViewName("redirect:/departments");
        } catch (Exception e) {
            log.error("already exists");
            modelAndView.addObject("departErr", "depart.err");
            modelAndView.setViewName("add_department");
        }
        return modelAndView;
    }

    @RequestMapping("/delete_depart/{id}")
    public String deleteCourse(@PathVariable long id) {
        departmentService.deleteDepart(id);
        return "redirect:/departments";
    }
}
