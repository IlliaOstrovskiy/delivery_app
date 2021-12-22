package com.ilya.trpz.repository;

import com.ilya.trpz.model.Package;
import com.ilya.trpz.model.StatusPackage;
import com.ilya.trpz.model.User;
import com.ilya.trpz.model.UserPackages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPackagesRepository extends JpaRepository<UserPackages, Long> {
    UserPackages findByNewPackageId(Long id);
    List<UserPackages> findUserPackagesBySender(User user);
    List<UserPackages> findUserPackagesByRecipient(User user);
    void deleteById(Long id);
}
