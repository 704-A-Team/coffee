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
		
		// Paths.get(...)는 단순히 **경로 정보(Path 객체)**를 생성해줄 뿐
		// 실제 파일이나 폴더의 존재 여부와는 관계 없어!
		
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
	
	// delete
	public void deleteFiles(List<String> oldFileNames) {
		if(oldFileNames == null || oldFileNames.size() == 0) return;
		int result = 0;
		oldFileNames.forEach(oldFile -> {
			String thumnailFilename = "s_" + oldFileNames;
			Path thunmailPath = Paths.get(uploadPath,thumnailFilename);
			Path filePath	  = Paths.get(uploadPath, oldFile);
			// real delete
			try {
				Files.deleteIfExists(thunmailPath);
				Files.deleteIfExists(filePath);
				// local variable result defined in an enclosing scope must be final or effectively final
				// 람다 안에서 지역변수는 읽기만 가능하다
				// 즉, final 처럼 값을 바꾸지 못한다
				// result = 1;
				System.out.println("delete result->"+result);
			} catch (IOException e) {
				// 입출력 작업 중 예외 : "외부 환경"에서 나온 에러(파일이 없다, 등)
				throw new RuntimeException(e.getMessage());
			}
			
		});
		
	}
	
	// File은 파일 + 경로 + 기능을 다 혼자 가짐 (올인원)
	// Path는 단순히 경로만 표현하는 객체
	// Files는 그 Path를 가지고 **기능(읽기, 삭제 등)**을 수행
	// ➡ 둘을 분리함으로써 "유연성"이 생긴 거야!
	
	// File → Java 1.0 / 단순하지만 기능 제한 / 유지보수 코드에 많음
	// Files + Path → Java 7부터 도입된 NIO.2 / 최신 프로젝트에서는 이게 표준!


	
	
	
}
