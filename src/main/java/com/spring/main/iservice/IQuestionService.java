package com.spring.main.iservice;

import java.util.List;

import com.spring.main.dto.QuestionAdminDto;
import com.spring.main.dto.QuestionStudentDto;
import com.spring.main.entity.Question;

public interface IQuestionService {
	List<QuestionStudentDto> getQuestionsForStudent(String title);
	Question createQuestion(Long quizId,Question quiestion);
	QuestionAdminDto  getQuestionById(Long id);
	List<Question> getAll();
	Question updateQuestion(Long id,Question question);
	QuestionAdminDto update(long id,QuestionAdminDto question);
	void deleteQuestionById(Long id);
	List<Question> getQuestionByTitle(String title);
}
