package com.example.fileupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class FileController {

	@GetMapping("/")
	public String start() {
		return "upload";
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("upload_file") MultipartFile multipartFile) {


		String contentType = multipartFile.getContentType();
		String parameterName = multipartFile.getName();
		String originalFileName = multipartFile.getOriginalFilename();

		System.out.println("originalFileName = " + originalFileName);

		long fileSize = multipartFile.getSize();

		try (InputStream inputStream = multipartFile.getInputStream()) {

			byte[] bytes = inputStream.readAllBytes();

			System.out.println(new String(bytes, StandardCharsets.UTF_8));

		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}

		return "complete";
	}
}
