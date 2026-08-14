package com.spring.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.dto.QuizAdminDto;
import com.spring.main.dto.QuizStudentDto;
import com.spring.main.iservice.IQuizRoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	 @Autowired
	    private IQuizRoleService quizRoleService;

	    @GetMapping("/admin")
	    @PreAuthorize("hasRole('ADMIN')")
	    public List<QuizAdminDto> adminQuiz() {

	        return quizRoleService.getQuizForAdmin();
	    }

	    @GetMapping("/student/questions")
	    @PreAuthorize("hasRole('STUDENT')")
	    public List<QuizStudentDto> studentQuiz() {

	        return quizRoleService.getQuizForStudent();
	    }
}
