package com.spring.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.Answer;
import com.spring.main.entity.QuizAttempt;

public interface QuizAttemptRepo extends JpaRepository<QuizAttempt, Long>{
    List<QuizAttempt> findByUser_Id(Long userId);

    List<QuizAttempt> findByQuiz_Id(Long quizId);
    long countByStatus(String status);
	//double findAverageScore();
    Optional<QuizAttempt> findByUser_IdAndQuiz_IdAndStatus(
            Long userId,
            Long quizId,
            String status);
    long countByUserIdAndStatus(Long userId, String status);
    List<QuizAttempt> findByUserId(Long userId);
}
