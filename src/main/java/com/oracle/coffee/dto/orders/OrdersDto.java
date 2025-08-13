
package com.oracle.coffee.dto.orders;

import java.math.BigDecimal;
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
	private BigDecimal order_final_price;
	private LocalDateTime order_req_date;	// 수주 요청 시간
	private LocalDateTime order_confirmed_date;	// 수주 승인/반려 시간
	private List<OrdersDetailDto> orders_details;
	
	
	// join 조회용
	
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
