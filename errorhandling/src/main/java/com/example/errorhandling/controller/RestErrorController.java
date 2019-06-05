package com.example.errorhandling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class RestErrorController extends AbstractErrorController {

	final String errorPath;

	public RestErrorController(
			ErrorAttributes errorAttributes,
			List<ErrorViewResolver> errorViewResolvers,
			@Value("${server.error.path:${error.path:/error}}") final String path
	) {
		super(errorAttributes, errorViewResolvers);
		this.errorPath = path;
	}

	@GetMapping
	public ResponseEntity<Object> error(final HttpServletRequest req){
		final Map errorAttributes = this.getErrorAttributes(req, true);

		return null;
	}

	@Override
	public String getErrorPath() {
		return errorPath;
	}
}
