package com.example.fileupload.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class FileForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
