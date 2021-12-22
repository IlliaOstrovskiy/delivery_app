package com.ilya.trpz.repository;

import com.ilya.trpz.model.RoleType;
import com.ilya.trpz.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
	User findByUsername(String username);
	User deleteByUsername(String username);
	User findByEmail(String email);
	List<User> findUsersByRole(RoleType role);
}
