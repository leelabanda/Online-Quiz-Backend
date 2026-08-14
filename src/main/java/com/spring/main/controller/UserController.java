package com.spring.main.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.dto.AttemptDto;
import com.spring.main.dto.QuizDto;
import com.spring.main.dto.UserDto;
import com.spring.main.dto.UserInfoDto;
import com.spring.main.dto.UserProfileDto;
import com.spring.main.entity.QuizAttempt;
import com.spring.main.entity.User;
import com.spring.main.iservice.IQuizAttemptService;
import com.spring.main.iservice.IQuizService;
import com.spring.main.iservice.IUserService;
import com.spring.main.repository.QuizRepo;
import com.spring.main.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final IUserService userService;
	private final IQuizAttemptService attemptService;
	private final QuizRepo quizRepo;
	@GetMapping
	public ResponseEntity<ApiResponse> getAllUsers(){
		List<User> users=userService.getAllUsers();
		return ResponseEntity.ok(new ApiResponse("Users Fetched Succesfully", users));
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> saveUser(@RequestBody User user){
		User users=userService.saveUser(user);
		return ResponseEntity.ok(new ApiResponse("User Added Successfully",users));
	}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
		User userId=userService.getUserById(id);
		return ResponseEntity.ok(new ApiResponse("User Found", userId));
	}
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id,@RequestBody User user){
		User updateUser=userService.updateUser(id, user);
		return ResponseEntity.ok(new ApiResponse("User Updated", updateUser));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.ok(new ApiResponse("User Deleted",null));
	}
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody UserDto loginUser){
		User user=userService.userLogin(loginUser.getEmail(), loginUser.getPassword());
		return ResponseEntity.ok(new ApiResponse("Login Successful",user));
	}
	@GetMapping("/users-with-attempts")
	public List<UserInfoDto> getUsers() {
	    return userService.getAllUsersWithAttempts();
	}
	@GetMapping("/attempts/user/{userId}")
	public List<QuizAttempt> getAttemptsByUser(@PathVariable Long userId) {
	    return attemptService.getAttemptsByUser(userId);
	}
	@GetMapping("/profile")
	public ResponseEntity<UserProfileDto> getProfile(Authentication authentication){
		String username=authentication.getName();
		UserProfileDto user=userService.getUserByEmail(username);
		return ResponseEntity.ok(user);
	}
	@PutMapping("/profile")
	public ResponseEntity<UserProfileDto> updateProfile(
	        @RequestBody UserProfileDto user,
	        Principal principal) {

	    UserProfileDto updatedUser = userService.updateProfile(principal.getName(), user);

	    return ResponseEntity.ok(updatedUser);
	}
	@GetMapping("/count")
	public ResponseEntity<QuizDto> getTotalQuiz(){
		QuizDto dto=new QuizDto();
		dto.setTitle(String.valueOf(quizRepo.count()));
		return ResponseEntity.ok(dto);
	}
	@GetMapping("/{id}/total-score")
	public ResponseEntity<Integer> getTotalScore(@PathVariable Long id) {

	    return ResponseEntity.ok(userService.getTotalScore(id));

	}
	@GetMapping("/{id}/average-score")
	public ResponseEntity<Double> getAverageScore(@PathVariable Long id) {

	    return ResponseEntity.ok(userService.getAverageScore(id));

	}
}
