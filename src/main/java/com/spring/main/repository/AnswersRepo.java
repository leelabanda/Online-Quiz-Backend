package com.spring.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.main.entity.Answer;

import jakarta.transaction.Transactional;

public interface AnswersRepo extends JpaRepository<Answer, Long>{
	Optional<Answer> findByQuizAttemptIdAndQuestionId(
            Long attemptId,
            Long questionId);

	List<Answer> findByQuizAttempt_Id(Long id);
	@Modifying
    @Transactional
    @Query("DELETE FROM Answer a WHERE a.question.id = :questionId")
    void deleteByQuestionId(@Param("questionId") Long questionId);

	 @Modifying
	    @Transactional
	    @Query("DELETE FROM Answer a WHERE a.quizAttempt.id = :attemptId")
	    void deleteByQuizAttempt_Id(@Param("attemptId") Long attemptId);
	
}
