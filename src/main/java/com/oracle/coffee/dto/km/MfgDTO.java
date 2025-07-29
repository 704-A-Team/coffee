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
public class MfgDTO {	// 생산지시
	private int 		mfg_code;			// 생산신청코드		
	private int 		product_wan_code;	// 완제품 제품코드
	private int 		mfg_amount;			// 생산수량
	private int 		mfg_status;			// 상태(요청 1, 완료 5 , 마감 6)
	private String 		mfg_ddate;			// 생산완료예정일
	private String 		mfg_contents;		// 비고
	private int			mfg_reg_code;		// 사원코드 (등록자)
	private LocalDateTime 	mfg_reg_date;		// 등록일 (신청일)
	
}
