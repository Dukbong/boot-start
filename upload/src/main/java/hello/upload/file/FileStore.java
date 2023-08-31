package hello.upload.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import hello.upload.domain.UploadFile;

@Component
public class FileStore {

	@Value("${file.dir}")
	private String fileDir;
	
	public String getFullPath(String fileName) {
		return fileDir + fileName;
	}
	
	public List<UploadFile> storeFiles(List<MultipartFile> files) throws IllegalStateException, IOException{
		List<UploadFile> storeFileResult = new ArrayList<>();
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				UploadFile uploadFile = storeFile(file);
				storeFileResult.add(uploadFile);
			}
		}
		return storeFileResult;
	}
	
	public UploadFile storeFile(MultipartFile file) throws IllegalStateException, IOException {
		if(file.isEmpty()) {
			return null;
		}
		
		String originFileName = file.getOriginalFilename();
		
		// 서버에 저장할 이름
		String storefileName = createStoreFileName(originFileName);
		
		// 파일 저장
		file.transferTo(new File(getFullPath(storefileName)));
		
		return new UploadFile(originFileName, storefileName);
	}
	private String createStoreFileName(String originFileName) {
		String ext = extractExt(originFileName);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;
	}
	
	private String extractExt(String originFileName) {
		// 확장자 뽑기 위함
		int pos = originFileName.lastIndexOf(".");
		String ext = originFileName.substring(pos + 1);
		return ext;
	}
}
