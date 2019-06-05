package com.example.errorhandling.controller;

import com.example.errorhandling.dto.ErrorResource;
import com.example.errorhandling.dto.ErrorResourceBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandlingControllerAdvance extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResource es = new ErrorResourceBuilder()
				.withMessage(ex.getMessage())
				.withTimestamp(LocalDateTime.now())
				.createErrorResource();
		return handleExceptionInternal(ex, es, headers, status, request);
	}


}
