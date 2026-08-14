package com.spring.main.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.main.dto.AttemptDto;
import com.spring.main.dto.UserDto;
import com.spring.main.dto.UserInfoDto;
import com.spring.main.dto.UserProfileDto;
import com.spring.main.entity.QuizAttempt;
import com.spring.main.entity.User;
import com.spring.main.exception.ResourceNotFoundException;
import com.spring.main.iservice.IUserService;
import com.spring.main.repository.QuizAttemptRepo;
import com.spring.main.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
	private final UserRepo userRepo;
	private final QuizAttemptRepo attemptRepo;
	@Autowired 
	private PasswordEncoder passwordEncoder;
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
		user.setCreatedAt(LocalDateTime.now());
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
	}

	@Override
	public User updateUser(Long id, User user) {
		// TODO Auto-generated method stub
		 User existingUser = userRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("User not found"));
		    // Update password only if a new one is provided
		    if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
		        String encodedPassword = passwordEncoder.encode(user.getPassword());
		        existingUser.setPassword(encodedPassword);
		    }
	        existingUser.setName(user.getName());
	        existingUser.setEmail(user.getEmail());
	      //  existingUser.setPassword(encodedPassword);
	        existingUser.setRole(user.getRole());
	        return userRepo.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRepo.deleteById(id);
	}

	@Override
	public User userLogin(String email, String password) {
		User existing=userRepo.findByEmail(email); 
		if(existing==null) {
			throw new RuntimeException("Email In valid");
		}
		if(!existing.getPassword().equals(password)) {
			throw new RuntimeException("Password in valid");
		}
		// TODO Auto-generated method stub
		return existing;
	}

	@Override
	public void sendOtp(String email) {

	    User user = userRepo.findByEmail(email);

	    if (user == null) {
	        throw new RuntimeException("Email not found");
	    }

	    String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

	    // DEV MODE: just print OTP
	    System.out.println("OTP for " + email + " = " + otp);
	}

	@Override
	public UserInfoDto mapToDTO(User user) {
		// TODO Auto-generated method stub
		UserInfoDto dto = new UserInfoDto();
	    dto.setId(user.getId());
	    dto.setName(user.getName());
	    dto.setEmail(user.getEmail());
	    dto.setRole(user.getRole().name());

	    List<AttemptDto> attempts = user.getQuizAttempts()
	        .stream()
	        .map(attempt -> {
	            AttemptDto a = new AttemptDto();
	            a.setId(attempt.getId());
	            a.setScore(attempt.getScore());
	            a.setStatus(attempt.getStatus());
	            a.setAttemptedAt(attempt.getAttemptedAt());
	            
	            return a;
	        }).toList();
	    LocalDateTime latestCompleted=user.getQuizAttempts().stream()
	    		.map(a->a.getAttemptedAt()).filter(Objects::nonNull).max(LocalDateTime::compareTo).orElse(null);
	    dto.setCompletedAt(latestCompleted);
	    dto.setQuizAttempts(attempts);

	    return dto;
	}
	@Override
	public List<UserInfoDto> getAllUsersWithAttempts() {

	    List<User> users = userRepo.findAll();

	    return users.stream()
	            .map(this::mapToDTO)
	            .toList();
	}
	@Override
	public User findByEmail(String email) {
	    return userRepo.findByEmail(email);
	}
	@Override
	public UserProfileDto getUserByEmail(String email) {
		User user=userRepo.findByEmail(email);
		UserProfileDto userDto=new UserProfileDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		if(user.getRole()!=null) {
			userDto.setRole(user.getRole().name());
		}
		return userDto;
	}
	@Override
	public UserProfileDto updateProfile(String email,UserProfileDto updateUser) {
		User user=userRepo.findByEmail(email);
		UserProfileDto userDto=new UserProfileDto();
		if(user==null) {
			throw new RuntimeException("User Not Function");
		}
		// Update entity fields
	    user.setName(updateUser.getName());
	    user.setEmail(updateUser.getEmail());

	    // Update password only if provided

	    // Save updated entity
	    User updatedUser = userRepo.save(user);

	    // Convert Entity to DTO
	    UserProfileDto userDto1 = new UserProfileDto();
	    userDto1.setId(updatedUser.getId());
	    userDto1.setName(updatedUser.getName());
	    userDto1.setEmail(updatedUser.getEmail());

	    if (updatedUser.getRole() != null) {
	        userDto1.setRole(updatedUser.getRole().name());
	    }

	    return userDto1;
	}

	@Override
	public Integer getTotalScore(Long id) {
		// TODO Auto-generated method stub
		List<QuizAttempt>attempts=attemptRepo.findByUserId(id);
		return attempts.stream().mapToInt(QuizAttempt::getScore).sum();
	}

	@Override
	public Double getAverageScore(Long userId) {
		// TODO Auto-generated method stub
		List<QuizAttempt>attempts=attemptRepo.findByUserId(userId);
		if(attempts.isEmpty()) {
			return 0.0;
		}
		double average=attempts.stream().mapToInt(QuizAttempt::getScore).average().orElse(0.0);
		return Math.round(average * 10.0) / 10.0;
	}
}
