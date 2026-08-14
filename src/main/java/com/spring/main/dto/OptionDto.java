package com.spring.main.dto;

import java.time.LocalDateTime;

import com.spring.main.entity.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
	private Long id;
	private String optionText;
	private Question question;
}
