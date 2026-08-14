package com.spring.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
	
	private int score;
    private int totalQuestions;
    private int maxMarks;
    private int correctAnswers;
    private int wrongAnswers;
}
