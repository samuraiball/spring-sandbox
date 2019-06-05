package com.example.errorhandling.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ErrorResource implements Serializable {

	public ErrorResource(LocalDateTime timestamp, String message, List<ErrorDto> errors) {
		this.timestamp = timestamp;
		this.message = message;
		this.errors = errors;
	}

	private final LocalDateTime timestamp;
	private final String message;
	private final List<ErrorDto> errors;

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public List<ErrorDto> getErrors() {
		return errors;
	}

	public static class ErrorDto {

		public ErrorDto(String code, String field) {
			this.code = code;
			this.field = field;
		}


		private final String code;
		private final String field;

		public String getCode() {
			return code;
		}

		public String getField() {
			return field;
		}
	}
}
