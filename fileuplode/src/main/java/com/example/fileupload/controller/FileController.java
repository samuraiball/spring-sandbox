package com.example.fileupload.controller;

import com.example.fileupload.model.FileStoreService;
import com.example.fileupload.model.eintity.UploadedFile;
import com.example.fileupload.model.eintity.UploadedFileBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
		//TODO: create Main page
		return "";
	}

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("fileList", fileStoreService.findAllFileList());
		return "/list";
	}

	@GetMapping("/file/{fileId:[0-9]*}")
	public ResponseEntity<InputStream> file(@PathVariable String fileId) {
		//TODO: download file
//		UploadedFile uploadedFile = fileStoreService.findById(fileId);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_TYPE, uploadedFile.getMimeType())
//				.header(HttpHeaders.CONTENT_DISPOSITION,
//						"attachment; filename=\"" + uploadedFile.getFileName() + "\"")
//				.body();
		return null;
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
		return "redirect:/complete";
	}

	@GetMapping("/complete")
	public String complete() {
		return "complete";
	}
}
