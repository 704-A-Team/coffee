package com.oracle.coffee.dto.km;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MfgRpDetailDTO {
	private int 			mfg_code;			// 생산신청코드
	private int 			product_code;		// 완제품 제품코드
	private int 			mfg_qty;			// 실제완료생산량(EA)	   (완제품 증가)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime	mfg_end_date;		// 실제 생산 완료 날짜  (수불마감 시 필요)
	private int 			mfg_mat;			// 실제 투입 원재료 총중량(g)
	private int 			mfg_end;			// 실제 생산 완료  총중량(g)
	private double 			yield;				// 실제 수율(%)
	private double 			pct;				// 펀차(소수점 첫째 자리)
	private String 			note;				// 비고
	private int 			mfg_rp_reg_code;	// 사원번호(신청인과 다를 수 있다)
	private LocalDateTime 	report_reg_date;	// 등록일
	

	private List<RpDetailDTO> 		rp_detail;
	
	private int				mfg_status;
	private String			product_won_name;
	private String			cd_contents;		// 원재료 단위
	private String			product_name;		// 완제품 이름
	private String			emp_name;
	private String			emp_tel;
	private String			dept_name;
	
	private String 			mfgEndDateStr; // JSP에서 안전하게 포맷된 생산완료일 표시용

}
