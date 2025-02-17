package com.schoolmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

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
