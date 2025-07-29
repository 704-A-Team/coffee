
package com.oracle.coffee.dto.order;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
	private LocalDate order_reg_date;
	private List<OrdersDetailDto> orders_details;
}
