package com.spring.main.dto;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDtoAngular {
    private Long id;
    private Long quizId;
    private String quizTitle;
    private Integer score;
    private String status;
    private LocalDateTime attemptedAt;
}
