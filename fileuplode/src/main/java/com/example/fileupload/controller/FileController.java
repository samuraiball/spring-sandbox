package com.example.fileupload.controller;

import com.example.fileupload.model.FileStoreService;
import com.example.fileupload.model.eintity.UploadedFile;
import com.example.fileupload.model.eintity.UploadedFileBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class FileController {

	private FileStoreService fileStoreService;

	public FileController(FileStoreService fileStoreService) {
		this.fileStoreService = fileStoreService;
	}


	@GetMapping("/")
	public String start() {
		return "upload";
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("upload_file") MultipartFile multipartFile) {

		byte[] fileBody;
		try (InputStream inputStream = multipartFile.getInputStream()) {
			fileBody = inputStream.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}

		fileStoreService.store(
				new UploadedFileBuilder()
						.withFileBody(fileBody)
						.withFileName(multipartFile.getOriginalFilename())
						.withMimeType(multipartFile.getContentType())
						.createUploadedFile());
		UploadedFile uploadedFile = fileStoreService.findById("0");
		System.out.println(new String(uploadedFile.getFileBody(), StandardCharsets.UTF_8));
		return "complete";
	}
}
