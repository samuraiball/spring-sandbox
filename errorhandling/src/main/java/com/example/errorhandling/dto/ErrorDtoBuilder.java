package com.example.errorhandling.dto;

public class ErrorDtoBuilder {
	private String code;
	private String field;

	public ErrorDtoBuilder withCode(String code) {
		this.code = code;
		return this;
	}

	public ErrorDtoBuilder withField(String field) {
		this.field = field;
		return this;
	}

	public ErrorResource.ErrorDto createErrorDto() {
		return new ErrorResource.ErrorDto(code, field);
	}
}