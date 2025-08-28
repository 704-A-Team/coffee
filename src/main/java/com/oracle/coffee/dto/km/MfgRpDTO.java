package com.oracle.coffee.dto.km;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MfgRpDTO {		// 생산보고
	private int 			mfg_code;			// 생산신청코드
	private int 			product_code;		// 완제품 제품코드
	private int 			mfg_qty;			// 실제완료생산량(EA)	   (완제품 증가)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime	mfg_end_date;		// 실제 생산 완료 날짜  (수불마감 시 필요)
	private int 			mfg_mat;			// 실제 투입 원재료 총중량(g)
	private int 			mfg_end;			// 실제 생산 완료  총중량(g)
	private BigDecimal 		yield;				// 실제 수율(%)
	private BigDecimal 		pct;				// 펀차(소수점 첫째 자리)
	private String 			note;				// 비고
	private int 			mfg_rp_reg_code;	// 사원번호(신청인과 다를 수 있다)
	private LocalDateTime 	report_reg_date;	// 등록일
	
	
	
	private String			product_name;
	private String			product_won_name;
	private int				mfg_amount;
	private int 			product_yield;		// 예상수율
	private int				product_pack;		// 생산단위
	private int				product_weight;		// 기본중량
	private int				product_won_code;	// 원재료 코드
	private int				recipe_amount;		// 원재료 소모량
	private int				target_amount;		// 생산신청 수 * 원재료 소모량

}
