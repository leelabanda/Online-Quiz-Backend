package com.spring.main.iservice;

import java.util.List;

import com.spring.main.dto.AdminStatsDto;
import com.spring.main.dto.AnswerSubmissionDto;
import com.spring.main.dto.QuizAttemptDto;
import com.spring.main.dto.QuizDtoAngular;
import com.spring.main.dto.QuizReportDto;
import com.spring.main.dto.QuizResultDto;
import com.spring.main.entity.Answer;
import com.spring.main.entity.QuizAttempt;

public interface IQuizAttemptService {
	QuizAttempt createAttempt(QuizAttemptDto request);
	List<QuizAttempt> getAllQuizes();
	QuizAttempt getQuizAttempt(Long id);
	void deleteById(Long Id);
	QuizAttempt UpdateById(Long id,QuizAttemptDto request);
	List<QuizAttempt> getAttemptsByUser(Long userid);
	List<QuizAttempt> getAttemptsByQuiz(Long quizId);
	AdminStatsDto getAdminStats();
	int submitQuiz(Long attemptId, AnswerSubmissionDto request);
	QuizResultDto getQuizResult(Long attemptId);
	List<QuizDtoAngular> getStudentResults(Long userId);
	long getCompletedQuizCount(Long userId);
	List<QuizReportDto> getStudentReport(Long quizId);
}
