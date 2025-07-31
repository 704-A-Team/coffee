
package com.oracle.coffee.dto.orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDto {
	private int order_code;
	private int orders_client_code;
	private int orders_perm_code;
	private String order_note;
	private String order_refuse;
	private int order_status;
	private LocalDateTime order_reg_date;
	private List<OrdersDetailDto> orders_details;
	
	
	// join 조회용
	
	// bunryu 테이블
	private String cd_contents;		// 상태 내용
	
	/*
	// client_tb 
	private String client_name;		// 상호명
	private String client_address;	// 가맹점 주소
	private String client_tel;		// 가맹점 번호
	private String saup_num;		// 사업자등록번호
	private String boss_name;		// 가맹점 대표자명
	private int client_emp_code;	// 담당직원 코드
	
	// emp 테이블
	private String emp_name;		// 담당직원 이름
	private String emp_tel;			// 담당직원 연락처
	*/
}
