package com.spring.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.main.dto.UserDto;
import com.spring.main.entity.User;
import com.spring.main.security.JwtService;
import com.spring.main.repository.UserRepo;

@Service
public class UserSecurity implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User existingUser=userRepo.findByEmail(username);
        if (existingUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(existingUser.getEmail())
                .password(existingUser.getPassword())
                .authorities(existingUser.getRole().name())// encoded password
                .build();
    }

}
