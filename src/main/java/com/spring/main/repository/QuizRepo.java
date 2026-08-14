package com.spring.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.Quiz;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

	Quiz findByTitle(String title);

//	Quiz findByTitleWithOptions(String title);

}
