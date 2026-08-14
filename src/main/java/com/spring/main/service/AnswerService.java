package com.spring.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.main.dto.AnswerDto;
import com.spring.main.dto.AnswerReviewDto;
import com.spring.main.dto.PreviousAnswersContinueDto;
import com.spring.main.entity.Answer;
import com.spring.main.entity.Option;
import com.spring.main.entity.Question;
import com.spring.main.entity.QuizAttempt;
import com.spring.main.exception.ResourceNotFoundException;
import com.spring.main.iservice.IAnswerService;
import com.spring.main.repository.AnswersRepo;
import com.spring.main.repository.OptionRepo;
import com.spring.main.repository.QuestionRepo;
import com.spring.main.repository.QuizAttemptRepo;
import com.spring.main.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService{
	private final AnswersRepo answerRepo;
    private final QuizAttemptRepo quizAttemptRepository;
    private final QuestionRepo questionRepository;
    private final OptionRepo optionRepository;
@Override
public Answer saveAnswer(AnswerDto answer) {

    QuizAttempt attempt = quizAttemptRepository
            .findById(answer.getAttemptId())
            .orElseThrow(() ->
                    new RuntimeException("Quiz Attempt not found"));


    Question question = questionRepository
            .findById(answer.getQuestionId())
            .orElseThrow(() ->
                    new RuntimeException("Question not found"));


    Option option = optionRepository
            .findById(answer.getSelectedOptionId())
            .orElseThrow(() ->
                    new RuntimeException("Option not found"));



    Optional<Answer> existingAnswer =
            answerRepo.findByQuizAttemptIdAndQuestionId(
                    attempt.getId(),
                    question.getId()
            );


    Answer answerEntity;


    if(existingAnswer.isPresent()) {


        // Update existing answer

        answerEntity = existingAnswer.get();


        // Remove old score
        if(Boolean.TRUE.equals(answerEntity.getIsCorrect())) {

            int oldScore =
                    attempt.getScore() == null ? 0 : attempt.getScore();

            attempt.setScore(
                    oldScore - question.getMarks()
            );

        }


        answerEntity.setSelectedOption(option);

        answerEntity.setIsCorrect(
                option.getIsCorrect()
        );


    }
    else {


        // Create new answer

        answerEntity = new Answer();

        answerEntity.setQuizAttempt(attempt);

        answerEntity.setQuestion(question);

        answerEntity.setSelectedOption(option);

        answerEntity.setIsCorrect(
                option.getIsCorrect()
        );

    }



    Answer saved =
            answerRepo.save(answerEntity);



    // Recalculate score

    List<Answer> allAnswers =
            answerRepo.findByQuizAttempt_Id(
                    attempt.getId()
            );


    int score = 0;


    for(Answer a : allAnswers){

        if(Boolean.TRUE.equals(a.getIsCorrect())){

            score += a.getQuestion().getMarks();

        }

    }


    attempt.setScore(score);

    quizAttemptRepository.save(attempt);



    return saved;

}

	@Override
	public List<Answer> getAll() {
		// TODO Auto-generated method stub
		return answerRepo.findAll();
	}

	@Override
	public Answer getById(Long id) {
		// TODO Auto-generated method stub
		Answer answerId=answerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Id Not Found")); 
		return null;
	}

	@Override
	public Answer updateAnswer(Long id, Answer answer) {
		// TODO Auto-generated method stub
		Answer existingAnswer=answerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Id Not Found"));
		existingAnswer.setIsCorrect(answer.getIsCorrect());
		existingAnswer.setQuestion(answer.getQuestion());
		existingAnswer.setQuizAttempt(answer.getQuizAttempt());
		existingAnswer.setSelectedOption(answer.getSelectedOption());
		return answerRepo.save(existingAnswer);
	}

	@Override
	public void deleteAnswer(Long id) {
		// TODO Auto-generated method stub
		Answer deleteById=answerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Id Not Found"));
		answerRepo.deleteById(id);
	}

	@Override
	public List<AnswerReviewDto> getSubmittedAnswers(Long attemptId) {
		// TODO Auto-generated method stub
		List<Answer> ans=answerRepo.findByQuizAttempt_Id(attemptId);
		return ans.stream().map(answer->{
			Question question=answer.getQuestion();
			String correctOption=question.getOptions()
					.stream()
					.filter(option->Boolean.TRUE.equals(option.getIsCorrect()))
					.findFirst()
					.map(Option::getOptionText)
					.orElse("");
			return new AnswerReviewDto(
					question.getId(),
					question.getQuestionText(),
					answer.getSelectedOption().getOptionText(),
					correctOption,
					answer.getIsCorrect(),
					question.getMarks());
		}).toList();
	}

	@Override
	public List<PreviousAnswersContinueDto> getPreviousAnswers(Long attemptId) {
		// TODO Auto-generated method stub
		List<Answer> answers=answerRepo.findByQuizAttempt_Id(attemptId);
		return answers.stream().map(a->new PreviousAnswersContinueDto(a.getQuestion().getId(),a.getSelectedOption().getId())).toList();
	}
		// TODO Auto-generated method stub
		
}
