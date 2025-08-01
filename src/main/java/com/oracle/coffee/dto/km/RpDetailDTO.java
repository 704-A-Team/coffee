package com.oracle.coffee.dto.km;

import lombok.Data;

@Data
public class RpDetailDTO {			// 생산보고 원재료 상세
	private int 	mfg_code;			// 생산신청코드
	private int 	product_won_code;	// 원재료 제품코드
	private int 	real_amount;		// 실제 사용량
	private int 	trash_amount;		// 폐기 수량
	private String 	trash_contents;		// 비고
}
