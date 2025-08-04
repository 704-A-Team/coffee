
package com.oracle.coffee.dto.orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersListDto {
	private int order_code;
	private int orders_client_code;
	private int order_status;
	private LocalDateTime order_reg_date;
	
	
	// join 조회용
	
	// bunryu 테이블
	private String cd_contents;		// 상태 내용
	
	// client_tb 
	private String client_name;		// 상호명
	
	// emp 테이블
	private String emp_name;		// 담당자 이름
	
}
