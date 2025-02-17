package com.schoolmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolmanagementsystem.entity.School;


public interface SchoolRepository extends JpaRepository<School, Long> {

}
