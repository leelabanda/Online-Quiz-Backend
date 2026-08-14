package com.spring.main.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.dto.LoginResponseDto;
import com.spring.main.dto.UserDto;
import com.spring.main.entity.User;
import com.spring.main.iservice.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private IUserService userService;
	@Autowired
	private UserSecurity userSecurity;
	@Autowired
	private JwtService jwtService;

	@GetMapping("/login")
	public String loginPage() {
		return "Hello Welcome.";
	}

	@PostMapping("/login")
	public LoginResponseDto login(@RequestBody UserDto request) {
		System.out.println("Login API called");
	    System.out.println("Email = " + request.getEmail());
	    System.out.println("Password = " + request.getPassword());
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		if (authentication.isAuthenticated()) {
			UserDetails userDetails=userSecurity.loadUserByUsername(request.getEmail());
			System.out.println("Login email: " + request.getEmail());
			System.out.println("UserDetails username: " + userDetails.getUsername());
	        String token =
	                jwtService.generateToken(userDetails);

	        String role =
	                userDetails.getAuthorities()
	                           .iterator()
	                           .next()
	                           .getAuthority();
	        User user = userService.findByEmail(request.getEmail());
	        System.out.println("TOKEN = " + token);
	        System.out.println("ROLE = " + role);
	        System.out.println("UserId= "+user);
			return  new LoginResponseDto(token,role,user.getId(),user.getName());
		}
		

		throw new RuntimeException("Invalid Login");
	}

//	@PostMapping("/register")
//	public String register(@RequestBody User user) {
//	    try {
//
//	        System.out.println("Register API Called");
//	        System.out.println(user);
//
//	        userService.saveUser(user);
//
//	        return "User Successfully";
//
//	    } catch (Exception e) {
//
//	        e.printStackTrace();
//
//	        return "Not Registerd";
//	    }
//	    }
	@PostMapping("/register")
	public LoginResponseDto register(@RequestBody User user) {

	    System.out.println("Register API Called");
	    System.out.println("Email = " + user.getEmail());

	    // Save the user
	    userService.saveUser(user);

	    // Load saved user
	    User savedUser = userService.findByEmail(user.getEmail());

	    // Load UserDetails
	    UserDetails userDetails = userSecurity.loadUserByUsername(savedUser.getEmail());

	    // Generate JWT
	    String token = jwtService.generateToken(userDetails);

	    // Get role
	    String role = userDetails.getAuthorities()
	            .iterator()
	            .next()
	            .getAuthority();

	    System.out.println("TOKEN = " + token);
	    System.out.println("ROLE = " + role);

	    // Return response
	    return new LoginResponseDto(
	            token,
	            role,
	            savedUser.getId(),
	            savedUser.getName()
	    );
	}	@GetMapping("/me")
	public User getCurrentUser(Authentication authentication) {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
	    String email = auth.getName();

	    return userService.findByEmail(email);
	    
	}

	@GetMapping
	public String getMessage(HttpServletRequest request) {
		return "greeting <br/>" + request.getSession().getId();
	}

	@GetMapping("/csrf-token")
	public CsrfToken getToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestBody Map<String, String> request) {

	    String email = request.get("email");

	    userService.sendOtp(email);

	    return "OTP Sent Successfully";
	}
	
}
