package com.spring.main.iservice;

import java.util.List;

import com.spring.main.dto.AnswerDto;
import com.spring.main.dto.AnswerReviewDto;
import com.spring.main.dto.PreviousAnswersContinueDto;
import com.spring.main.entity.Answer;

public interface IAnswerService {
	Answer saveAnswer(AnswerDto answer);
	List<Answer> getAll();
	Answer getById(Long id);
	Answer updateAnswer(Long id,Answer answer);
	void deleteAnswer(Long id);
	List<AnswerReviewDto> getSubmittedAnswers(Long attemptId);
	List<PreviousAnswersContinueDto> getPreviousAnswers(Long attemptId);
}
