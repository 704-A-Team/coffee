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
public class WanAndRecipeDTO {
		private int 		product_code;		 // 제품코드
		private String  	product_name;		 // 제품명
		private String  	product_contents;	 // 제품설명
		private int     	product_unit; 		 // 제품단위(EA 0, g 1 , ml 2)
		private int 		product_type;		 // 제품유형(완제품 1, 원재료 0) 
		private int 		product_yield;		 // 예상 수율
		private int			product_weight;		 // 기본 중량
		private boolean 	product_isorder;	 // 제품납품여부 ( 납품 안함 0 , 납품 1)
		private int			product_pack;		 // 완제품 생산 단위
		private boolean 	product_isdel;		 // 삭제 구분 (기본 0)
		private int			product_reg_code;	 // 사원코드 (등록자)
		private LocalDateTime 	product_reg_date;// 등록일 (현재 날짜)
		private int			wan_price;			 // 완제품 최근 가격 
		
		private String 		simage;
		
		/*
		 * private int product_wan_code; private int product_won_code; private int
		 * recipe_amount;
		 */
		
		// 여러 개의 레시피
		 private List<RecipeDTO> recipeList;
		 
		// 여러 개의 이미지
		 private List<ProductImgDTO> wanImgList;
		
		// Files
		@Builder.Default
		private List<MultipartFile> file = new ArrayList<>();
		@Builder.Default
		private List<String> uploadFileNames = new ArrayList<>();
		
		
}
