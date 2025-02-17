package com.schoolmanagementsystem.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {

	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

	public MessageResponse(final int statusCode,final Date timestamp,final String message, final String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;		
	}
}
