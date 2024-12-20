package com.schoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagementsystem.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	public long countBySchoolId(final long id);
}
