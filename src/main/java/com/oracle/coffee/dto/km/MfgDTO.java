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
public class MfgDTO {	// 생산신청
	private int 		mfg_code;			// 생산신청코드		
	private int			mfg_reg_code;		// 사원코드 (등록자)
	private LocalDateTime 	mfg_reg_date;	// 등록일 (신청일)
	
	// 조회용
	private String      pageNum;  
	private int 		start; 		 	   
	private int 		end;
	private String      currentPage;
	
	private String 		emp_name;
	private String		dept_name;
	
}
