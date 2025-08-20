
package com.oracle.coffee.dto.orders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class OrdersListDto {
	private int order_code;
	private int orders_client_code;
	private int order_status;
	private BigDecimal order_final_price;			// 수주 확정 금액
	
	private LocalDateTime order_reg_date;
	private LocalDateTime order_req_date;			// 수주 요청 시간
	private LocalDateTime order_confirmed_date;		// 수주 승인/반려 시간
	
	// jsp 프린트용
	public String reg_date() {
		if (order_reg_date == null) return "";
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(order_reg_date);
	}
	public String req_date() {
		if (order_req_date == null) return "";
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(order_req_date);
	}
	public String confirmed_date() {
		if (order_confirmed_date == null) return "";
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(order_confirmed_date);
	}

	
	// join 조회용
	
	// bunryu 테이블
	private String cd_contents;		// 상태 내용
	
	// client_tb 
	private String client_name;		// 상호명
	
	// emp 테이블
	private String emp_name;		// 담당자 이름
	
}
