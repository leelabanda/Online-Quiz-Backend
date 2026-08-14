package com.spring.main.iservice;

import java.util.List;

import com.spring.main.entity.Quiz;

public interface IQuizService {
	Quiz addQuiz(Quiz quiz);
	List<Quiz> getAllQuiz();
	Quiz getQuizById(Long id);
	Quiz updateQuiz(Long id,Quiz quiz);
	void deleteQuiz(Long id);
}
