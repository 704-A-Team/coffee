package com.oracle.oBootBoard03.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {
	
	// 1.
	// 이미지 업로드 파일 경로 지정
	// 멤버변수
	@Value("${com.oracle.oBootTodoApi01.upload.path}")
	private String uploadPath;
	//   	   1.1 uploadPath는 처음에 "uploads" 라는 상대 경로 문자열로 들어가 있어
	
	// 2.
	// 업로드 경로 만들었으면
	// CustomFileUtil이 실행되었을 때 upload 파일이 없으면 만들어주기
	@PostConstruct
	private void init() {
		File tempFolder = new File(uploadPath);
		if( !tempFolder.exists() ) {
			tempFolder.mkdir();
		}
		uploadPath = tempFolder.getAbsolutePath();
		// 	   1.2 상대 경로 → 절대 경로로 변환

	}
	
	// 3.
	public List<String> save(List<MultipartFile> file) {
		log.info("save Start");
		if(file == null || file.size()  == 0) {
			return null;
		}
		
		List<String> uploadFileNames = new ArrayList<>();
		
		for(MultipartFile files : file) {
			String savedName = UUID.randomUUID().toString() + "_" + files.getOriginalFilename();
			Path   savePath	 = Paths.get(uploadPath, savedName);
			//                               경로에 이름으로 저장할 준비
			// 업로드
			try {
				Files.copy(files.getInputStream(), savePath);
				String contentType = files.getContentType();
				if(contentType != null && contentType.startsWith("image")) {
					Path thumbnailPath = Paths.get(uploadPath, "s_"+savedName);
					
					Thumbnails.of(savePath.toFile())
							  .size(200, 150)
							  .toFile(thumbnailPath.toFile());
				}
				uploadFileNames.add(savedName);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
		return uploadFileNames;
	}
	
	
	
}
