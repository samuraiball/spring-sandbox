package com.example.fileupload.controller;

import com.example.fileupload.form.FileForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {

	@GetMapping("/")
	public String start(){
		return "upload";
	}

	@PostMapping("/upload")
	public String upload(FileForm fileForm){

		MultipartFile multipartFile = fileForm.getFile();

		String contentType = multipartFile.getContentType();
		String parameterName = multipartFile.getName();
		String originalFileName = multipartFile.getOriginalFilename();

		long fileSize = multipartFile.getSize();

		try(InputStream inputStream = multipartFile.getInputStream()){
			System.out.println("inputStream = " + inputStream.toString());
		}catch (IOException e){
			e.printStackTrace();
			return "error";
		}

		return "complete";
	}
}
