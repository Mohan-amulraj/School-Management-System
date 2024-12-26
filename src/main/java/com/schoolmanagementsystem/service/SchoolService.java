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

	public School createSchool(final School school) {
		return this.schoolRepository.save(school);
	}

	public List<School> getAllSchool() {
		return this.schoolRepository.findAll();
	}

	public School getSchoolById(final Long id) {
		return this.schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
	}

	public String updateSchool(final Long id, final School schoolDetails) {
		School school = schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
		school.setAddress(schoolDetails.getAddress());
		this.schoolRepository.save(school);
		return "School id:" + id + " " + "successfully updated";
	}

	public String deleteSchool(final Long id) {
		School school = schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
		this.schoolRepository.delete(school);
		return "School id:" + id + " " + "successfully deleted";
	}

	public Long countSchool() {
		return this.schoolRepository.count();
	}

	public Page<School> getSchoolPage(final int pageIndex, final int pageSize, final String field) {
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
		return this.schoolRepository.findAll(pageable);
	}
}