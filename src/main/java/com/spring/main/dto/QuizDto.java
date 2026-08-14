package com.spring.main.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.main.entity.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
	private Long id;
    private String title;
    private String description;
    private Integer duration;
//    @JsonIgnore
//    private List<QuestionDTO> questions;
}
