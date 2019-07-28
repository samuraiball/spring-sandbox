package com.example.fileupload.model.eintity;


import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.TableGenerator;

@Entity
@Table(name = "files")
public class UploadedFile {

	public UploadedFile() {
	}

	public UploadedFile(
			Integer fileId, String fileName,
			String mimeType, byte[] fileBody
	) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.fileBody = fileBody;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@TableGenerator(pkColumnValue = "file_id")
	@Column(name = "file_id")
	private Integer fileId;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "mime_type")
	private String mimeType;

	@Column(name = "file_body")
	private byte[] fileBody;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getFileBody() {
		return fileBody;
	}

	public void setFileBody(byte[] fileBody) {
		this.fileBody = fileBody;
	}
}
