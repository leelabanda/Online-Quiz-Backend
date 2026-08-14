package com.spring.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.main.dto.UserDto;
import com.spring.main.dto.UserProfileDto;
import com.spring.main.entity.Role;
import com.spring.main.entity.User;

public interface UserRepo extends JpaRepository<User,Long>{

	//UserInfoDto findByEmail(String email);
	User findByEmail(String email);
//	List<User> findByRole(String role);
//	List<User> findByRole(User role);
	User findByName(String name);
	long countByRole(Role role);
	//UserProfileDto findByEmail1(String email);
}
