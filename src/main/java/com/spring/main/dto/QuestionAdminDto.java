package com.spring.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAdminDto {
	 private Long id;
	 private String questionText;
	 private Integer marks;
	    private Long quizId;
	    private String quizTitle;
	 private List<OptionAdminDto> options;
}
