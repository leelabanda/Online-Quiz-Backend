package com.spring.main.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.main.entity.Quiz;
import com.spring.main.exception.ResourceNotFoundException;
import com.spring.main.iservice.IQuizService;
import com.spring.main.repository.QuizRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService implements IQuizService{
	private final QuizRepo quizRepo;
	@Override
	public Quiz addQuiz(Quiz quiz) {
		// TODO Auto-generated method stub
		quiz.setCreatedAt(LocalDateTime.now());
		return quizRepo.save(quiz);
	}

	@Override
	public List<Quiz> getAllQuiz() {
		// TODO Auto-generated method stub
		return quizRepo.findAll();
	}

	@Override
	public Quiz getQuizById(Long id) {
		// TODO Auto-generated method stub
		return quizRepo.findById(id).get();
	}

	@Override
	public Quiz updateQuiz(Long id, Quiz quiz) {
		// TODO Auto-generated method stub
		Quiz quizExisting =quizRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Quiz Id is not Fount"));
		quizExisting.setTitle(quiz.getTitle());
		quizExisting.setDescription(quiz.getDescription());
		quizExisting.setDuration(quiz.getDuration());
		return quizRepo.save(quizExisting);
	}

	@Override
	public void deleteQuiz(Long id) {
		// TODO Auto-generated method stub
		quizRepo.deleteById(id);
	}

}
