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
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.dto.AnswerDto;
import com.spring.main.entity.Answer;
import com.spring.main.iservice.IAnswerService;
import com.spring.main.iservice.IQuestionService;
import com.spring.main.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Controller
@CrossOrigin(origins="*")
@RequestMapping("api/answer")
public class AnswerController {
	private final IAnswerService answerService;
	
	@GetMapping
	public ResponseEntity<ApiResponse> getAllAnswers(){
		List<Answer> allAnswers=answerService.getAll();
		return ResponseEntity.ok(new ApiResponse("Fetched All",allAnswers));
	}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getAnswersById(@PathVariable Long id){
		Answer byId=answerService.getById(id);
		return ResponseEntity.ok(new ApiResponse("Successfully Fetched",byId));
	}
	@PostMapping
	public ResponseEntity<ApiResponse> addAnswers(@RequestBody AnswerDto answer){
		 System.out.println("========== ANSWER API CALLED ==========");
		Answer addAnswer=answerService.saveAnswer(answer);
		return ResponseEntity.ok(new ApiResponse("Added Successfully",addAnswer));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateAnswer(@PathVariable Long id,@RequestBody Answer answer){
		Answer updateAnswer=answerService.updateAnswer(id, answer);
		return ResponseEntity.ok(new ApiResponse("Updated Succesfully",updateAnswer));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
		answerService.deleteAnswer(id);
		return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));

	}
	@GetMapping("/{attemptId}/answers")
	public ResponseEntity<ApiResponse> getSubmittedAnswers(@PathVariable Long attemptId){
		return ResponseEntity.ok(new ApiResponse("Submitted Answers",answerService.getSubmittedAnswers(attemptId)));
	}
	@GetMapping("/attempt/{attemptId}/answers")
	public ResponseEntity<ApiResponse> getPreviousAnswers(
	        @PathVariable Long attemptId){

	    return ResponseEntity.ok(
	        new ApiResponse(
	            "Previous Answers",
	            answerService.getPreviousAnswers(attemptId)
	        )
	    );
	}

}
