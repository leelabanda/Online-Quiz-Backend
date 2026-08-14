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

import com.spring.main.dto.QuestionAdminDto;
import com.spring.main.entity.Question;
import com.spring.main.entity.Quiz;
import com.spring.main.iservice.IQuestionService;
import com.spring.main.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/question")
public class QuestionController {
	private final IQuestionService questionService;
	
	@GetMapping
	public ResponseEntity<ApiResponse> getAllQuestions(){
		List<Question> questions=questionService.getAll();
		return ResponseEntity.ok(new ApiResponse("Fetched All",questions));
	}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getById(@PathVariable Long id){
		QuestionAdminDto questionId=questionService.getQuestionById(id);
		return ResponseEntity.ok(new ApiResponse("Fetched All",questionId));
	}
	@GetMapping("/title")
	public ResponseEntity<ApiResponse> getQuestionByTitle(@RequestParam String title){
		List<Question> getQuestionsByTitle=questionService.getQuestionByTitle(title);
		return ResponseEntity.ok(new ApiResponse("Questions Found",getQuestionsByTitle));
	}
	@GetMapping("/student/title")
	public ResponseEntity<ApiResponse> getStudentQuestions(@RequestParam String title){

	    return ResponseEntity.ok(new ApiResponse("Questions Found",questionService.getQuestionsForStudent(title)));
	}
	@PostMapping
	public ResponseEntity<ApiResponse> addQuestion(@RequestParam Long quiz,@RequestBody Question question){
		Question addQuestion=questionService.createQuestion(quiz, question);
		return ResponseEntity.ok(new ApiResponse("Added Question Successfully",addQuestion));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateQuestionById(@PathVariable Long id,@RequestBody QuestionAdminDto question){
		QuestionAdminDto updateQuestion=questionService.update(id, question);
		return ResponseEntity.ok(new ApiResponse("Updated Successfully",updateQuestion));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
		questionService.deleteQuestionById(id);
		return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));
	}
	}
