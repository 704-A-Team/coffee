package com.oracle.coffee.dto.km;

import java.time.LocalDateTime;

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

}
