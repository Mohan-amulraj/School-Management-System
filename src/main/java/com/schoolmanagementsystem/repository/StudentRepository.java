package com.schoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolmanagementsystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public Long countBySchoolId(final Long id);

}
