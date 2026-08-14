package com.spring.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.main.entity.*;
public interface OptionRepo extends JpaRepository<Option, Long> {

}
