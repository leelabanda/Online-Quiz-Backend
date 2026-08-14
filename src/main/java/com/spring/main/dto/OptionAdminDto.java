package com.spring.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionAdminDto {
	private Long id;
	 private String optionText;
	    private boolean isCorrect;
}
