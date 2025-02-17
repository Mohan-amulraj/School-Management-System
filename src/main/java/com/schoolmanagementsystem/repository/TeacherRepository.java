package com.schoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolmanagementsystem.entity.Teacher;


public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	Long countBySchoolId(final Long id);
}
