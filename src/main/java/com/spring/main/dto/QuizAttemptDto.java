package com.spring.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptDto {
    private Long userId;
    private Long quizId;
    private Integer score;
    private String status;
}
