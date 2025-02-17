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

	public School updateSchool(final Long id, final School schoolDetails) {
		final School school = this.schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
		school.setId(schoolDetails.getId());
		return this.schoolRepository.save(school);		
	}

	public void deleteSchool(final Long id) {
		final School school = this.schoolRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("School not found for this id : " + id));
		this.schoolRepository.delete(school);
	}

	public Long countSchool() {
		return this.schoolRepository.count();
	}

	public Page<School> getSchoolPage(final int index, final int size, final String field) {
		final Sort sort = Sort.by(Sort.Direction.ASC, field);
		final Pageable pageable = PageRequest.of(index, size, sort);
		return this.schoolRepository.findAll(pageable);
	}
}