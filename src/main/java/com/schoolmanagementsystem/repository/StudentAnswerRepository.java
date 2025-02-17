package com.schoolmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolmanagementsystem.entity.StudentAnswer;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

	public List<StudentAnswer> findStudentById(Long id);

}