package com.spring.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Long> {

	List<Question> findByQuizTitle(String title);
}
