
package com.oracle.coffee.dto.orders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	private BigDecimal order_final_price;
	private LocalDateTime order_req_date;	// 수주 요청 시간
	private LocalDateTime order_confirmed_date;	// 수주 승인/반려 시간
	private List<OrdersDetailDto> orders_details;
	
	
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
	
	// mainPage 조회용
	private String  	clientName;
	private BigDecimal 	month_total_price;
	private String 		productName;
	
	// bunryu 테이블
	private String cd_contents;		// 상태 내용
	
	public BigDecimal calculateTotalPrice() {
		BigDecimal total = BigDecimal.ZERO;
		for(OrdersDetailDto detail : orders_details) {
			if (detail.getPrice() == null) continue;
			total = total.add(
						detail.getPrice().multiply(new BigDecimal(detail.getOrder_amount()))
					);
		}
		return total;
	}
}
