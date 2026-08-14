package com.spring.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerReviewDto {
	private Long questionId;
	private String question;
	private String selectedOption;
	private String correctOption;
	private boolean correct;
	private Integer marks;
}
