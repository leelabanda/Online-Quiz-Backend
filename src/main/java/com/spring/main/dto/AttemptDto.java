package com.spring.main.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttemptDto {
	private Long id;
    private Integer score;
    private LocalDateTime attemptedAt;
    private String status;
}
