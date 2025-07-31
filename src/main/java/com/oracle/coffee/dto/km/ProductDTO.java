package com.oracle.coffee.dto.km;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {	// 제품
	private int 		product_code;		 // 제품코드
	private String  	product_name;		 // 제품명
	private String  	product_contents;	 // 제품설명
	@Builder.Default
	private int     	product_unit 	= 0; // 제품단위(EA 0, g 1 , ml 2)
	@Builder.Default
	private int 		product_type 	= 1; // 제품유형(완제품 1, 원재료 0) 
	private int 		product_yield;		 // 예상 수율
	@Builder.Default
	private int			product_weight 	= 1; // 기본 중량
	@Builder.Default
	private boolean 	product_isorder = true; // 제품납품여부 ( 납품 안함 0 , 납품 1)
	private int			product_pack;		 // 완제품 생산 단위
	@Builder.Default
	private boolean 	product_isdel 	= false; // 삭제 구분 (기본 0)
	private int			product_reg_code;	 // 사원코드 (등록자)
	@Builder.Default
	private LocalDateTime 	product_reg_date = LocalDateTime.now();	 // 등록일 (현재 날짜)
	
	private String simage;
	private int    price;
	private String start_date;
	
	private String pageNum;
	private int start;
	private int end;
	private String currentPage;
	
	// Files
	@Builder.Default
	private List<MultipartFile> file = new ArrayList<>();
	@Builder.Default
	private List<String> uploadFileNames = new ArrayList<>();
	
	
}
