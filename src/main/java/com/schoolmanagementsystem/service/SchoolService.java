package com.schoolmanagementsystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.entity.School;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.SchoolRepository;

@Service
public class SchoolService {
	@Autowired
	private SchoolRepository schoolRepository;

	public School createSchool(School school) {
		return this.schoolRepository.save(school);
	}

	public List<School> getAllSchool() {
		return this.schoolRepository.findAll();
	}

	public School getSchoolById(Long id) {
		return this.schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
	}

	public String updateSchool(Long id, School schoolDetails) {
		School school = schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
		school.setAddress(schoolDetails.getAddress());
		this.schoolRepository.save(school);
		return "School id:" + id + " " + "successfully updated";
	}

	public String deleteSchool(Long id) {
		School school = schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
		this.schoolRepository.delete(school);
		return "School id:" + id + " " + "successfully deleted";
	}

	public long countSchool() {
		return schoolRepository.count();
	}

	public Page<School> getSchoolPage(int pageIndex, int pageSize, String field) {
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return this.schoolRepository.findAll(pageable);
	}
}