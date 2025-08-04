package com.oracle.coffee.dto.km;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MfgRpDTO {		// 생산보고
	private int 		mfg_code;			// 생산신청코드
	private int 		mfg_qty;			// 실제완료생산량(EA)
	private int 		mfg_mat;			// 실제 투입 원재료 총중량(g)
	private int 		mfg_end;			// 실제 생산 완료  총중량(g)
	private int 		yield;				// 실제 수율(%)
	private double 		pct;				// 펀차(소수점 첫째 자리)
	private String 		note;				// 비고
	private int 		mfg_rp_reg_code;	// 사원번호(신청인과 다를 수 있다)
	private LocalDateTime 	report_reg_date;	// 등록일

}
