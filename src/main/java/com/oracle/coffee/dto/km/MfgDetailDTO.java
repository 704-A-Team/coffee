package com.oracle.coffee.dto.km;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MfgDetailDTO {	// 제품별 생산신청 상세
	private int 			mfg_code;			// 생산신청코드	
	private int 			product_code;		// 완제품 제품코드
	private int 			mfg_amount;			// 생산수량
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime 	mfg_request_date;	// 생산요청일(영업/재고)
	private String 			mfg_due_date;		// 생산완료예정일(생산본부장)
	private int 			mfg_status;			// 상태(요청 1 , 승인 4 , 거부 3 , 완료 5 , 마감 6)
	private String 			mfg_contents;		// 비고(요청에 대한 처리내역)
	
	private LocalDateTime 	mfg_reg_date;
	private String 			product_name;
	private String 			emp_name;
	private String 			emp_tel;
	private String			dept_name;
	private String			cd_contents;        // 단위
	private int				mfg_qty;
	
	// 조회용
	private String      	pageNum;  
	private int 			start; 		 	   
	private int 			end;
	private String      	currentPage;
	// approve_mfg_detail Procedure  조회명
	private int  				 result_status;   //  Procedure 에서 결과(성공 1 & 실패0)
    private List<WonCodeLackDTO> listWonCodeLackDTO = new ArrayList<>();
}
