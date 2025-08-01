package com.oracle.coffee.dto.km;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceDTO {	// 제품 가격
	private int			product_code;   // 제품코드
	private String 		start_date;		// 가격변동시작일자 , sysdate 안되나?
	private int 		price_reg_code;	// 사원코드(등록자)
	@Builder.Default
	private String 		end_date = "9999-12-31";	// 가격변동종료일자
	private int 		price;			// 가격
	private LocalDateTime 	price_reg_date; // 등록일

}
