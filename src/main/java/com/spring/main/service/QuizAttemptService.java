package com.spring.main.service;

import com.spring.main.repository.AnswersRepo;
import com.spring.main.repository.OptionRepo;
import com.spring.main.repository.QuestionRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.main.dto.AdminStatsDto;
import com.spring.main.dto.AnswerSubmissionDto;
import com.spring.main.dto.AttemptDto;
import com.spring.main.dto.QuizAttemptDto;
import com.spring.main.dto.QuizDtoAngular;
import com.spring.main.dto.QuizReportDto;
import com.spring.main.dto.QuizResultDto;
import com.spring.main.entity.Answer;
import com.spring.main.entity.Option;
import com.spring.main.entity.Question;
import com.spring.main.entity.Quiz;
import com.spring.main.entity.QuizAttempt;
import com.spring.main.entity.Role;
import com.spring.main.entity.User;
import com.spring.main.exception.ResourceNotFoundException;
import com.spring.main.iservice.IQuizAttemptService;
import com.spring.main.repository.QuizAttemptRepo;
import com.spring.main.repository.QuizRepo;
import com.spring.main.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizAttemptService implements IQuizAttemptService {
	private final QuizAttemptRepo attemptRepo;
	private final UserRepo userRepo;
	private final QuizRepo quizRepo;
	private final QuestionRepo questionRepo;
	private final OptionRepo optionRepo;
	private final AnswersRepo answerRepo;

	@Override
	public List<QuizReportDto> getStudentReport(Long quizId) {
		List<QuizAttempt> attempts = attemptRepo.findByQuiz_Id(quizId);

		return attempts.stream()
				.map(attempt -> new QuizReportDto(attempt.getUser().getId(), attempt.getUser().getName(), // change if
																											// your User
																											// entity
																											// uses
																											// another
																											// field
						attempt.getUser().getEmail(), attempt.getScore(), attempt.getStatus(),
						attempt.getAttemptedAt()))
				.toList();
	}

	@Override
	public QuizAttempt createAttempt(QuizAttemptDto request) {
		// TODO Auto-generated method stub
		System.out.println("Received UserId : " + request.getUserId());
		System.out.println("Received QuizId : " + request.getQuizId());
		System.out.println("Received Status : " + request.getStatus());
		System.out.println("Received Score  : " + request.getScore());
		    // Only resume unfinished quiz
		    Optional<QuizAttempt> inProgressAttempt =
		            attemptRepo.findByUser_IdAndQuiz_IdAndStatus(
		                    request.getUserId(),
		                    request.getQuizId(),
		                    "IN_PROGRESS"
		            );

		    if (inProgressAttempt.isPresent()) {
		        return inProgressAttempt.get();
		    }


		    User user = userRepo.findById(request.getUserId())
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    Quiz quiz = quizRepo.findById(request.getQuizId())
		            .orElseThrow(() -> new RuntimeException("Quiz not found"));


		    // Always create new attempt
		    QuizAttempt attempt = new QuizAttempt();

		    attempt.setUser(user);
		    attempt.setQuiz(quiz);
		    attempt.setStatus("IN_PROGRESS");
		    attempt.setScore(0);
		    attempt.setAttemptedAt(LocalDateTime.now());


		    return attemptRepo.save(attempt);
		}

	@Override
	public List<QuizAttempt> getAllQuizes() {
		// TODO Auto-generated method stub
		return attemptRepo.findAll();
	}

	@Override
	public QuizAttempt getQuizAttempt(Long id) {
		// TODO Auto-generated method stub
		return attemptRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attempt Not Found"));
	}

	@Override
	public void deleteById(Long Id) {
		QuizAttempt delete = attemptRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		// TODO Auto-generated method stub
		attemptRepo.deleteById(Id);
	}

	@Override
	public QuizAttempt UpdateById(Long id, QuizAttemptDto request) {
		QuizAttempt existingAttempt = attemptRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Attempt not found"));

		existingAttempt.setScore(request.getScore());
		existingAttempt.setStatus(request.getStatus());

		// CRITICAL: If these blocks are missing, Hibernate won't update the foreign
		// keys!
		if (request.getUserId() != null) {
			User user = userRepo.findById(request.getUserId()).orElse(null);
			existingAttempt.setUser(user);
		}

		if (request.getQuizId() != null) {
			Quiz quiz = quizRepo.findById(request.getQuizId()).orElse(null);
			existingAttempt.setQuiz(quiz);
		}

		return attemptRepo.save(existingAttempt);
	}

	@Override
	public List<QuizAttempt> getAttemptsByUser(Long userId) {
		// TODO Auto-generated method stub
		QuizAttempt byUser = attemptRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found"));

		return attemptRepo.findByUser_Id(userId);
	}

	@Override
	public List<QuizAttempt> getAttemptsByQuiz(Long quizId) {
		// TODO Auto-generated method stub
		QuizAttempt byQuiz = attemptRepo.findById(quizId)
				.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return attemptRepo.findByQuiz_Id(quizId);
	}

	@Override
	public AdminStatsDto getAdminStats() {

		long totalAttempts = attemptRepo.count();

		long totalUsers = userRepo.countByRole(Role.STUDENT);

		System.out.println("STUDENT COUNT = " + totalUsers);

		long inProgress = attemptRepo.countByStatus("IN_PROGRESS");

		long completed = attemptRepo.countByStatus("COMPLETED");

		return new AdminStatsDto(totalAttempts, totalUsers, inProgress, completed);
	}

@Override
@Transactional
public int submitQuiz(Long attemptId, AnswerSubmissionDto request) {

    QuizAttempt attempt =
            attemptRepo.findById(attemptId)
            .orElseThrow(() -> new RuntimeException("Attempt Not Found"));


    // remove previous saved answers
    answerRepo.deleteByQuizAttempt_Id(attemptId);


    int totalScore = 0;


    for(AnswerSubmissionDto.AnswerDto ans : request.getAnswers()) {

        Question question =
                questionRepo.findById(ans.getQuestionId())
                .orElseThrow();


        Option option =
                optionRepo.findById(ans.getSelectedOptionId())
                .orElseThrow();


        boolean correct =
                Boolean.TRUE.equals(option.getIsCorrect());


        Answer answer = new Answer();

        answer.setQuizAttempt(attempt);
        answer.setQuestion(question);
        answer.setSelectedOption(option);
        answer.setIsCorrect(correct);


        answerRepo.save(answer);


        if(correct){
            totalScore += question.getMarks();
        }
    }


    attempt.setScore(totalScore);
    attempt.setStatus("COMPLETED");

    attemptRepo.save(attempt);


    return totalScore;
}

	@Override
	public QuizResultDto getQuizResult(Long attemptId) {
		// TODO Auto-generated method stub
		QuizAttempt attempt = attemptRepo.findById(attemptId)
				.orElseThrow(() -> new RuntimeException("Attempt Not Found"));
		List<Answer> answers = answerRepo.findByQuizAttempt_Id(attemptId);
		int correct = 0;
		int wrong = 0;
		int totalMarks = 0;
		for (Answer ans : answers) {
			if (Boolean.TRUE.equals(ans.getIsCorrect())) {
				correct++;
				totalMarks += ans.getQuestion().getMarks();
			} else {
				wrong++;
			}
		}
		QuizResultDto dto = new QuizResultDto();
		dto.setScore(attempt.getScore());
		dto.setCorrectAnswers(correct);
		dto.setWrongAnswers(wrong);
		dto.setTotalQuestions(answers.size());
		int maxMarks = answers.stream().mapToInt(a -> a.getQuestion().getMarks()).sum();
		dto.setMaxMarks(maxMarks);

		return dto;
	}

	@Override
	public List<QuizDtoAngular> getStudentResults(Long userId) {

		List<QuizAttempt> attempts = attemptRepo.findByUser_Id(userId);

		return attempts
				.stream().map(attempt -> new QuizDtoAngular(attempt.getId(),attempt.getQuiz().getId(),attempt.getQuiz().getTitle(),
						attempt.getScore(), attempt.getStatus(), attempt.getAttemptedAt()))
				.collect(Collectors.toList());
	}

	@Override
	public long getCompletedQuizCount(Long userId) {
		// TODO Auto-generated method stub
		return attemptRepo.countByUserIdAndStatus(userId, "COMPLETED");
	}
}
