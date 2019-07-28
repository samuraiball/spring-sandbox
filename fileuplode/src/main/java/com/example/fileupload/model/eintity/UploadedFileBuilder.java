package com.example.fileupload.model.eintity;

public class UploadedFileBuilder {
	private Integer fileId;
	private String fileName;
	private String mimeType;
	private byte[] fileBody;

	public UploadedFileBuilder withFileId(Integer fileId) {
		this.fileId = fileId;
		return this;
	}

	public UploadedFileBuilder withFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public UploadedFileBuilder withMimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}

	public UploadedFileBuilder withFileBody(byte[] fileBody) {
		this.fileBody = fileBody;
		return this;
	}

	public UploadedFile createUploadedFile() {
		return new UploadedFile(fileId, fileName, mimeType, fileBody);
	}
}