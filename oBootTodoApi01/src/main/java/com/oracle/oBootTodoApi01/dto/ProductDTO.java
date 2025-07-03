package com.oracle.oBootTodoApi01.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	
	private Long 	pno;
	private String 	pname;
	private int 	price;
	private String 	pdesc;	// 제품설명
	private String 	keyword;
	private boolean delFlag;
	
	// 조회용
	private String simage;  // 대표 사진 1장
	private String pageNum;
	private int start;
	private int end;
	// Page 정보
	private String currentPage;
	
	@Builder.Default   // 파일 업로드용, 	업로드 전 상태
	private List<MultipartFile> files = new ArrayList<>(); // new ArrayList<>()가 초기값이다 이거 없으면 files = null 이다
	@Builder.Default  // 업로드된 파일명,  	업로드 후 결과
	private List<String> uploadFileNames = new ArrayList<>();
}
