package com.spring.main.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizReportDto {

    private Long studentId;
    private String studentName;
    private String email;
    private Integer score;
    private String status;
    private LocalDateTime attemptedAt;
}