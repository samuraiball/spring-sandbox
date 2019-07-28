package com.example.fileupload.model;

import com.example.fileupload.model.eintity.UploadedFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileStoreService {

	private FileDao fileDao;

	public FileStoreService(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public Integer store(UploadedFile uploadedFile) {
		return fileDao.save(uploadedFile);
	}

	public UploadedFile findById(String fileId) {
		return fileDao.findById(fileId);
	}

	public List<UploadedFile> findAllFileList(){
		return fileDao.findAllFileList();
	}

}
