package com.example.fileupload.model;

import com.example.fileupload.model.eintity.UploadedFile;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.stereotype.Repository;

@Dao
@ConfigAutowireable
public interface FileDao {
	@Insert
	int save(UploadedFile uploadedFile);

	@Select
	UploadedFile findById(String fileId);
}
