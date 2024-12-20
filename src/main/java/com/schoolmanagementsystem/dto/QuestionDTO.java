package com.schoolmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDTO {

	private Long id;
	private String question;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
}
