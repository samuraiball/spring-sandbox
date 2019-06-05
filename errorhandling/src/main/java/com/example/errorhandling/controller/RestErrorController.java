package com.example.errorhandling.controller;

import com.example.errorhandling.dto.ErrorResource;
import com.example.errorhandling.dto.ErrorResourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
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
	public ResponseEntity<Object> error(final HttpServletRequest req) {
		final Map errorAttributes = this.getErrorAttributes(req, true);

		// "/error"を直に叩いてきた場合
		if (errorAttributes.get("status").equals(999)) {
			return new ResponseEntity<>(Collections.EMPTY_MAP, HttpStatus.OK);
		}

		HttpStatus status = this.getStatus(req);

		StringBuffer message = new StringBuffer();

		if (status.is4xxClientError()) {
			message.append(errorAttributes.get("message"))
					.append(" [path: ")
					.append(errorAttributes.get("path"))
					.append("]");
		} else {
			message.append("unknown error occurred ");
		}

		ErrorResource body = new ErrorResourceBuilder()
				.withMessage(message.toString())
				.withTimestamp(LocalDateTime.now())
				.withErrors(Collections.emptyList())
				.createErrorResource();

		return new ResponseEntity(body, status);
	}

	@Override
	public String getErrorPath() {
		return errorPath;
	}
}
