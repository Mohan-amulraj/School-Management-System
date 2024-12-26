package com.schoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagementsystem.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
