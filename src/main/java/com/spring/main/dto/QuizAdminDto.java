package com.spring.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAdminDto {
	 private Long id;
	    private String title;
	    private String description;

	    private List<QuestionAdminDto> questions;
}
