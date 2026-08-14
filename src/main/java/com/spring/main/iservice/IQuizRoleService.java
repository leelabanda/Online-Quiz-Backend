package com.spring.main.iservice;

import java.util.List;

import com.spring.main.dto.QuizAdminDto;
import com.spring.main.dto.QuizStudentDto;

public interface IQuizRoleService {
    List<QuizAdminDto> getQuizForAdmin();

    List<QuizStudentDto> getQuizForStudent();

}
