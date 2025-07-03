package com.oracle.oBootTodoApi01.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Component  // 클래스 단위, -> DI할 목적있 bean은 메소드 단위
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {
	
	@Value("${com.oracle.oBootTodoApi01.upload.path}")
	private String uploadPath; // 위에 경로에 파일을 생성해 준다 11:22
	
	@PostConstruct // 생성자가 실행되자 마자 같이 실행된다
	public void init() {
		File tempFolder = new File(uploadPath);
		// 폴더가 존재하지 않으면
		if(tempFolder.exists() == false) {
			tempFolder.mkdir(); // folder 만들어 준다
		}
		uploadPath = tempFolder.getAbsolutePath();
		log.info("=============== CustomFileUtil uploadPath==================");
		log.info(uploadPath);
	}
	
	// 업로드 하는 메소드
	public List<String> saveFiles(List<MultipartFile> files) {
		if(files == null || files.size() == 0) {
			return null;
		}
		
		List<String> uploadNames = new ArrayList<>();
		for(MultipartFile multipartFile : files) {  // 이름 세팅
			String savedName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
			//                   멤버변수 uploadPath에 savedName을 저장한다
			Path savePath = Paths.get(uploadPath, savedName);
			
			System.out.println("upload saveFiles() savedName: " + savedName);
			// real UPload
			try {	// 11:37 이미지 올리는데 설명
				Files.copy(multipartFile.getInputStream(), savePath);
				String contentType = multipartFile.getContentType();
				// 올린 파일의 종류
				if(contentType != null && contentType.startsWith("image")) {
					Path thumbnailPath = Paths.get(uploadPath, "s_"+savedName);
					
					Thumbnails.of(savePath.toFile())
							  .size(400, 400)
							  .toFile(thumbnailPath.toFile());  // 섬네일 올리기 ( 단지 규격이 있다) + 53행
				}
				
				uploadNames.add(savedName);
				
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}    // end-For
		
		return uploadNames;
	}

	public void deleteFiles(List<String> fileNames) {
		if(fileNames == null || fileNames.size() == 0 ) return;
		
		// stream 없어도 쓰네?  --> collect에서도 forEach 있다
		fileNames.forEach(fileName -> {
			// 섬네일 만들어 뒀으니 삭제하기
			String 	thumbnailFileName = "s_" + fileName;    // 선언
			Path 	thumbnailPath	  = Paths.get(uploadPath, thumbnailFileName);
			Path 	filePath		  = Paths.get(uploadPath, fileName);
			try { // real remove
				Files.deleteIfExists(thumbnailPath);  // 경로가 존재하면 삭제
				Files.deleteIfExists(filePath);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		});
		
	}
}
