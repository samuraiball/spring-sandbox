package com.example.errorhandling.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResourceBuilder {
	private LocalDateTime timestamp;
	private String message;
	private List<ErrorResource.ErrorDto> errors;

	public ErrorResourceBuilder withTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public ErrorResourceBuilder withMessage(String message) {
		this.message = message;
		return this;
	}

	public ErrorResourceBuilder withErrors(List<ErrorResource.ErrorDto> errors) {
		this.errors = errors;
		return this;
	}

	public ErrorResource createErrorResource() {
		return new ErrorResource(timestamp, message, errors);
	}
}