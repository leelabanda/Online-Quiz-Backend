package com.spring.main.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.main.dto.QuestionAdminDto;
import com.spring.main.dto.QuestionStudentDto;
import com.spring.main.dto.QuizAdminDto;
import com.spring.main.dto.QuizStudentDto;
import com.spring.main.entity.Quiz;
import com.spring.main.iservice.IQuizRoleService;
import com.spring.main.repository.QuizRepo;
import com.spring.main.dto.OptionAdminDto;
import com.spring.main.dto.OptionStudentDto;
@Service
public class QuizRoleService implements IQuizRoleService{
	@Autowired
    private QuizRepo quizRepo;

    @Override
    public List<QuizAdminDto> getQuizForAdmin() {

        return quizRepo.findAll()
                .stream()
                .map(this::convertAdmin)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizStudentDto> getQuizForStudent() {

        return quizRepo.findAll()
                .stream()
                .map(this::convertStudent)
                .collect(Collectors.toList());
    }

    private QuizAdminDto convertAdmin(Quiz quiz) {

        QuizAdminDto dto = new QuizAdminDto();

        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());

        List<QuestionAdminDto> questionDtos =
                quiz.getQuestions().stream().map(question -> {

                    QuestionAdminDto qdto = new QuestionAdminDto();

                    qdto.setId(question.getId());
                    qdto.setQuestionText(question.getQuestionText());
                    qdto.setMarks(question.getMarks());

                    List<OptionAdminDto> optionDtos =
                            question.getOptions().stream().map(option -> {

                                OptionAdminDto odto = new OptionAdminDto();

                                odto.setId(option.getId());
                                odto.setOptionText(option.getOptionText());
                                odto.setCorrect(option.getIsCorrect());

                                return odto;

                            }).collect(Collectors.toList());

                    qdto.setOptions(optionDtos);

                    return qdto;

                }).collect(Collectors.toList());

        dto.setQuestions(questionDtos);

        return dto;
    }

    private QuizStudentDto convertStudent(Quiz quiz) {

        QuizStudentDto dto = new QuizStudentDto();

        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());

        List<QuestionStudentDto> questionDtos =
                quiz.getQuestions().stream().map(question -> {

                    QuestionStudentDto qdto = new QuestionStudentDto();

                    qdto.setId(question.getId());
                    qdto.setQuestionText(question.getQuestionText());
                    qdto.setMarks(question.getMarks());

                    List<OptionStudentDto> optionDtos =
                            question.getOptions().stream().map(option -> {

                                OptionStudentDto odto = new OptionStudentDto();

                                odto.setId(option.getId());
                                odto.setOptionText(option.getOptionText());

                                return odto;

                            }).collect(Collectors.toList());

                    qdto.setOptions(optionDtos);

                    return qdto;

                }).collect(Collectors.toList());

        dto.setQuestions(questionDtos);

        return dto;
    }
}
