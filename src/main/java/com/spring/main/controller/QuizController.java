package com.spring.main.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.entity.Quiz;
import com.spring.main.iservice.IQuizService;
import com.spring.main.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/quiz")
public class QuizController {
	private final IQuizService quizService;

	@GetMapping
	public ResponseEntity<ApiResponse> getAllQuizes() {
		List<Quiz> quiz = quizService.getAllQuiz();
		return ResponseEntity.ok(new ApiResponse("Found Successfully", quiz));
	}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getQuizById(@PathVariable Long id) {
		Quiz quiz = quizService.getQuizById(id);
		return ResponseEntity.ok(new ApiResponse("Found Successfully", quiz));
	}
	

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addQuiz(@RequestBody Quiz quiz) {
		Quiz addQuiz = quizService.addQuiz(quiz);
		return ResponseEntity.ok(new ApiResponse("Added Successfully", addQuiz));
	}

	@PutMapping("{id}")
	public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody Quiz quiz) {
		Quiz updateQuiz = quizService.updateQuiz(id, quiz);
		return ResponseEntity.ok(new ApiResponse("Updated Successfully", updateQuiz));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
		quizService.deleteQuiz(id);
		return ResponseEntity.ok(new ApiResponse("Deleted Successfully", null));
	}
	
}
