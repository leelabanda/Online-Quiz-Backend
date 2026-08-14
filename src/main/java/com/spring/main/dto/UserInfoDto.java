package com.spring.main.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
	 private Long id;
	    private String name;
	    private String email;
	    private String role;
	    private LocalDateTime completedAt;
	    private List<AttemptDto> quizAttempts;
}
