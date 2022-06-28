package com.ivman.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MultipartFile fileData;
	
	private String fileName;
	
	private String filePath;

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "FileUploadModel [fileData=" + fileData + ", fileName=" + fileName + ", filePath=" + filePath + "]";
	}
	
}
