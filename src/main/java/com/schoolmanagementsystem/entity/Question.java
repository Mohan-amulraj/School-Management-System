package com.schoolmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String question;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String crt_answer;

	@ManyToOne
	private Test test;

	@ManyToOne
	private Teacher teacher;

	@ManyToOne
	private Subject subject;
}
