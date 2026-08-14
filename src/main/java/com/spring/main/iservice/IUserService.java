package com.spring.main.iservice;

import java.util.List;
import java.util.Optional;

import com.spring.main.dto.UserDto;
import com.spring.main.dto.UserInfoDto;
import com.spring.main.dto.UserProfileDto;
import com.spring.main.entity.User;

public interface IUserService {
	
	User saveUser(User user);
	List<User> getAllUsers();
	User getUserById(Long id);
	User updateUser(Long id,User user);
	void deleteUser(Long id);
	//Optional<User> getUserById(Long id);
	User userLogin(String email,String password);
	void sendOtp(String email);
	UserInfoDto mapToDTO(User user);
	List<UserInfoDto> getAllUsersWithAttempts();
	User findByEmail(String email);
	UserProfileDto getUserByEmail(String email);
	UserProfileDto updateProfile(String email,UserProfileDto updateUser);
	Integer getTotalScore(Long id);
	Double getAverageScore(Long userId);
	
}
