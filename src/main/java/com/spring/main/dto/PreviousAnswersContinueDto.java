package com.spring.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviousAnswersContinueDto {
	private Long questionId;
	private Long selectedOptionId;
}
