package com.example.fileupload.controller;

import com.example.fileupload.form.FileForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

	@GetMapping("/")
	public String start(){
		return "upload";
	}

	@PostMapping("/download")
	public String upload(FileForm fileForm){

		MultipartFile multipartFile = fileForm.getFile();

		String contentType = multipartFile.getContentType();
		String parameterName = multipartFile.getName();
		String originalFileName = multipartFile.getOriginalFilename();

		return "complete";
	}
}
