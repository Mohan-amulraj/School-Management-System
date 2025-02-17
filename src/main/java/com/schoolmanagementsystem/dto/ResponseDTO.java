package com.schoolmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseDTO {

	private String status;
	private int statusCode;
	private String message;
	private Object data;

	public ResponseDTO(final String status, final int statusCode, final String message, 
			final Object data) {
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
}
