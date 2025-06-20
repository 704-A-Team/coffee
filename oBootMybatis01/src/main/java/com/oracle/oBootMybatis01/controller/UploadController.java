package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UploadController {
	
	
	
	// upLoadForm 시작 화면
	@RequestMapping("/upLoadFormStart")
	public String upLoadFormStart() {
		System.out.println("UploadController upLoadFormStart Start");
		return "upLoadFormStart";
	}
	
	// upLoad 1단계
	@RequestMapping(value = "uploadForm")
	public String uploadForm(HttpServletRequest request , Model model) throws IOException, ServletException {
		// 멀티 미디어를 받는 객체
		Part image = request.getPart("file1");
		InputStream inputStream = image.getInputStream(); // String 으로 바뀐다
		
		// 파일 확장자 구하기
		String fileName = image.getSubmittedFileName();
		String[] split = fileName.split("\\.");
		String originalName = split[split.length - 2];
		String suffix = split[split.length - 1];
		
		System.out.println("fileName-> "+fileName);
		System.out.println("originalName-> "+originalName);
		System.out.println("suffix-> "+suffix);
		
		// Servlet 상속 받지 못했을 때 realPath 불러 오는 방법
		String uploadPath = request.getSession()
								   .getServletContext()
								   .getRealPath("/upload1/");
	
		System.out.println("uploadForm POST Start");
		System.out.println("uploadForm uploadPath-> "+uploadPath);
		

		String savedName = uploadFile(originalName, suffix, inputStream, uploadPath);
		
		// service --> DB Logic(CRUD)  savedName  11:11 - 12
		
		System.out.println("Return savedName->" + savedName);
		model.addAttribute("savedName", savedName);
		
		return "uploadResult";
	}
	// 11:07 6/20
	private String uploadFile(String originalName
							, String suffix
							, InputStream inputStream
							, String uploadPath) throws FileNotFoundException, IOException {
		
		// universally unique identifier (UUID).         세계적으로 식별가능한 식별자
		UUID uid = UUID.randomUUID();
		
		System.out.println("uploadPath->"+uploadPath);
		
		// Directory 생성 방법
		File fileDirectory = new File(uploadPath);
		if(!fileDirectory.exists()) {
			// 신규 폴더(Directory) 생성
			fileDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName + "." + suffix;
		log.info("savedName : " + savedName);
		// 임시파일 생성
		File tempFile = new File(uploadPath+savedName);
		// 생성된 임시파일에 요청으로 넘어온 file의 inputStream 복사
		try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
			int read;
			// 2K * K
			// 원시적 방법의 업로드
			byte[] bytes = new byte[2048000];
			while ((read = inputStream.read(bytes)) != -1) {
				// Target File에 요청으로 넘어온 file의 inputStream 복사
				outputStream.write(bytes, 0, read);
			}
		} finally {
			System.out.println("UpLoad The End");
		} return savedName;
		
		// 스트링으로 넘겨주면 파일을 복사할 수 있다
	}
	
	@RequestMapping("/uploadFileDelete")
	public String uploadFileDelete(HttpServletRequest request, Model model) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload1/");
		// Backup Folder
		String delFile = request.getParameter("delFile");
		System.out.println("uploadFileDelete GET Start");
		String deleteFile = uploadPath + delFile;
		System.out.println("uploadFileDelete deleteFile-> " + deleteFile);
		int delResult = upFileDelete(deleteFile);
		model.addAttribute("deleteFile", deleteFile);
		model.addAttribute("delResult", delResult);
		
		return "uploadResult";
	}

	private int upFileDelete(String deleteFileName) {
		
		int result = 0;
		log.info("upFileDelete result->" + deleteFileName);
		File file = new File(deleteFileName);
		if( file.exists() ) {
			
			if( file.delete() ) {
				System.out.println("file 삭제 성공");
				result = 1;
			}
			else {
				System.out.println("file 삭제 실패");
				result = 0;
			}
		}
		else {
			System.out.println("삭제할 파일이 존재하지 않습니다");
			result = -1;
		}
		return result;
	}
	
}
