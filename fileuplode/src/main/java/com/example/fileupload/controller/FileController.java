package com.example.fileupload.controller;

import com.example.fileupload.model.FileStoreService;
import com.example.fileupload.model.eintity.UploadedFile;
import com.example.fileupload.model.eintity.UploadedFileBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class FileController {

	private FileStoreService fileStoreService;

	public FileController(FileStoreService fileStoreService) {
		this.fileStoreService = fileStoreService;
	}


	@GetMapping("/")
	public String start() {
		return "index";
	}

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("fileList", fileStoreService.findAllFileList());
		return "/list";
	}


	@GetMapping("/file/{fileId:[0-9]*}")
	@ResponseBody
	public ResponseEntity<byte[]> file(@PathVariable String fileId) {
		UploadedFile uploadedFile = fileStoreService.findById(fileId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", uploadedFile.getMimeType());
		headers.add("Content-Disposition",
				"attachment; filename=" +
						URLEncoder.encode(uploadedFile.getFileName(), StandardCharsets.UTF_8));
		return new ResponseEntity(uploadedFile.getFileBody(), headers, HttpStatus.OK);
	}

	@GetMapping("/uploadForm")
	public String startUpload(Model model) {
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

		return "redirect:/complete";
	}

	@GetMapping("/complete")
	public String complete() {
		return "complete";
	}
}
