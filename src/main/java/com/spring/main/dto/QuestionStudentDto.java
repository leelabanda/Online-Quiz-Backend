package com.spring.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionStudentDto {
	 private Long id;
	    private String questionText;
	    private Integer marks;
	    private List<OptionStudentDto> options;
}
