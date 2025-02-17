package com.schoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolmanagementsystem.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
