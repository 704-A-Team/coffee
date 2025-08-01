package com.oracle.coffee.dto.km;

import lombok.Data;

@Data
public class RecipeDTO {	// 완제품 생산 단위 레시피
	private int 		product_wan_code;	// 제품코드
	private int 		product_won_code;	// 원재료코드
	private int 		recipe_amount;		// 원재료 소모량(수량)
	private String 		won_product_name;	// 원재료 이름
}
