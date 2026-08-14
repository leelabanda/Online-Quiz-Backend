package com.spring.main.controller;

import java.util.List;
import java.util.Map;

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

import com.spring.main.dto.AdminStatsDto;
import com.spring.main.dto.AnswerSubmissionDto;
import com.spring.main.dto.QuizAttemptDto;
import com.spring.main.dto.QuizResultDto;
import com.spring.main.entity.QuizAttempt;
import com.spring.main.iservice.IQuizAttemptService;
import com.spring.main.iservice.IUserService;
import com.spring.main.repository.QuizAttemptRepo;
import com.spring.main.repository.UserRepo;
import com.spring.main.response.ApiResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/attempt")
public class QuizAttempController {
	private final IQuizAttemptService attemptService;
	private final UserRepo userRepo;
	private final QuizAttemptRepo attemptRepo;
	@GetMapping
	public ResponseEntity<ApiResponse> getAllAttempts(){
		List<QuizAttempt> getAll=attemptService.getAllQuizes();
		return ResponseEntity.ok(new ApiResponse("Fetched All",getAll));
	}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getAllAttempts(@PathVariable Long id){
		QuizAttempt quizAttempt=attemptService.getQuizAttempt(id);
		return ResponseEntity.ok(new ApiResponse("Fetched By iD Successfully",quizAttempt));
	}
	@GetMapping("/student/{userId}")
	public ResponseEntity<?> getStudentResults(
	@PathVariable Long userId){

	return ResponseEntity.ok(

	Map.of(
	"message","Success",
	"data",
	attemptService.getStudentResults(userId)
	)

	);

	}
	@GetMapping("/admin/stats")
	public AdminStatsDto getAdminStats() {
		 AdminStatsDto dto = new AdminStatsDto();

		    dto.setTotalAttempts(attemptRepo.count());
		    dto.setTotalUsers(userRepo.count());
		    dto.setCompleted(attemptRepo.countByStatus("COMPLETED"));
		    dto.setInProgress(attemptRepo.countByStatus("IN_PROGRESS"));
		   // dto.setAverageScore(attemptRepo.findAverageScore());
	    return dto;
	}
	@PostMapping
	public ResponseEntity<ApiResponse> addAttempt(@RequestBody QuizAttemptDto request){
	QuizAttempt add=attemptService.createAttempt(request);
	return ResponseEntity.ok(new ApiResponse("Added Successfully",add));
	}
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> update(@PathVariable Long id,@RequestBody QuizAttemptDto request){
		QuizAttempt update=attemptService.UpdateById(id, request);
		return ResponseEntity.ok(new ApiResponse("Update Sucessfully",update));
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
		attemptService.deleteById(id);
		return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));
	}
	@GetMapping("/user/{id}")
	public List<QuizAttempt> getByUser(@PathVariable Long id) {
	    return attemptService.getAttemptsByUser(id);
	}
	 @PostMapping("/{attemptId}/submit")
	    public ResponseEntity<Integer> submitQuiz(@PathVariable Long attemptId,@RequestBody AnswerSubmissionDto request) {

	        int score = attemptService.submitQuiz(attemptId, request);
	        return ResponseEntity.ok(score);
	    }
	 @GetMapping("/result/{attemptId}")
	 public ResponseEntity<QuizResultDto> getResult(@PathVariable Long attemptId) {

	     return ResponseEntity.ok(attemptService.getQuizResult(attemptId));
	 }
	 @GetMapping("/{id}/completed-count")
	 public Long getCompletedCount(@PathVariable Long id) {
		 return attemptService.getCompletedQuizCount(id);
	 }
	 @GetMapping("/report/{quizId}")
	 public ResponseEntity<ApiResponse> getStudentReport(@PathVariable Long quizId){

	     return ResponseEntity.ok(
	             new ApiResponse(
	                     "Student Report",
	                     attemptService.getStudentReport(quizId)
	             )
	     );
	 }
	}
