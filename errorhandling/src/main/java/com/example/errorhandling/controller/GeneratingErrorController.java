package com.example.errorhandling.controller;

import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
public class GeneratingErrorController {

	@GetMapping("/hello")
	public void error() {
		throw new RuntimeException("Hello, error");
	}

	@GetMapping("/helloNoHandler")
	public void errorMissingServletRequestParameterException() throws Exception {
		throw new NoHandlerFoundException("hello", "/helloNoHandler", new HttpHeaders());
	}

}
