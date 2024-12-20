package com.schoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagementsystem.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository <Teacher, Long>{
	
		long countBySchoolId(final long id);

}
