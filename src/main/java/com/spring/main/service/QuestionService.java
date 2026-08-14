package com.spring.main.service;
import com.spring.main.dto.OptionAdminDto;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.main.dto.OptionStudentDto;
import com.spring.main.dto.QuestionAdminDto;
import com.spring.main.dto.QuestionStudentDto;
import com.spring.main.entity.Option;
import com.spring.main.entity.Question;
import com.spring.main.entity.Quiz;
import com.spring.main.exception.ResourceNotFoundException;
import com.spring.main.iservice.IQuestionService;
import com.spring.main.repository.AnswersRepo;
import com.spring.main.repository.QuestionRepo;
import com.spring.main.repository.QuizRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {
	private final QuestionRepo questionRepo;
	private final AnswersRepo answerRepo;
	private final QuizRepo quizReo;

	@Override
	public Question createQuestion(Long quizId, Question question) {
		// TODO Auto-generated method stub
		Quiz existingQuiz = quizReo.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("Quiz Not Found"));
		question.setQuiz(existingQuiz);

		if (question.getOptions() != null) {
			for (Option option : question.getOptions()) {
				option.setQuestion(question);
			}
		}
		return questionRepo.save(question);
	}

	@Override
	public QuestionAdminDto getQuestionById(Long id) {

	    Question question = questionRepo.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Question not found with id: " + id));

	    QuestionAdminDto dto = new QuestionAdminDto();

	    dto.setId(question.getId());
	    dto.setQuestionText(question.getQuestionText());
	    dto.setMarks(question.getMarks());

	    dto.setQuizId(question.getQuiz().getId());
	    dto.setQuizTitle(question.getQuiz().getTitle());

	    dto.setOptions(
	        question.getOptions().stream()
	            .map(op -> {
	                OptionAdminDto optionDto = new OptionAdminDto();
	                optionDto.setId(op.getId());
	                optionDto.setOptionText(op.getOptionText());
	                optionDto.setCorrect(op.getIsCorrect());
	                return optionDto;
	            })
	            .toList()
	    );

	    return dto;
	}
	@Override
	public List<Question> getAll() {
		// TODO Auto-generated method stub
		return questionRepo.findAll();
	}

	@Override
	public Question updateQuestion(Long id, Question question) {
		// TODO Auto-generated method stub
		Question existingQuestion = questionRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

		existingQuestion.setQuestionText(question.getQuestionText());
		existingQuestion.setMarks(question.getMarks());

		return questionRepo.save(existingQuestion);
	}
	@Transactional
	@Override
	public void deleteQuestionById(Long id) {
		// TODO Auto-generated method stub
		   Question question = questionRepo.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException("Question not found with id: " + id));
		   answerRepo.deleteByQuestionId(id);
		   questionRepo.delete(question);
	}

	@Override
	public List<Question> getQuestionByTitle(String title) {
		return questionRepo.findByQuizTitle(title);
	}

	@Override
	public List<QuestionStudentDto> getQuestionsForStudent(String title) {
		// TODO Auto-generated method stub
		List<Question> questions=questionRepo.findByQuizTitle(title);
		return questions.stream()
	            .map(question -> {
	                QuestionStudentDto dto = new QuestionStudentDto();

	                dto.setId(question.getId());
	                dto.setQuestionText(question.getQuestionText());
	                dto.setMarks(question.getMarks());

	                List<OptionStudentDto> options = question.getOptions()
	                        .stream()
	                        .map(option -> new OptionStudentDto(
	                                option.getId(),
	                                option.getOptionText()))
	                        .toList();

	                dto.setOptions(options);

	                return dto;

	            }).toList();
	        }

	@Override
	public QuestionAdminDto update(long id, QuestionAdminDto question) {
		Question question1 = questionRepo.findById(id)
		        .orElseThrow(() -> new RuntimeException("Question not found"));

		QuestionAdminDto dto = new QuestionAdminDto();

		dto.setId(question1.getId());
		dto.setQuestionText(question1.getQuestionText());
		dto.setMarks(question1.getMarks());

		dto.setQuizId(question1.getQuiz().getId());
		dto.setQuizTitle(question1.getQuiz().getTitle());

		dto.setOptions(
		    question1.getOptions().stream()
		        .map(op -> new OptionAdminDto(
		                op.getId(),
		                op.getOptionText(),
		                op.getIsCorrect()))
		        .toList()
		);

		return dto;
	}
}
