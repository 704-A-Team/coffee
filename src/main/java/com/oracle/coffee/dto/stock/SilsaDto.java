package com.oracle.coffee.dto.stock;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SilsaDto {
	private String silsa_yrmo;
	private int silsa_product_code;
	private int silsa_status = 4;
	private int silsa_amount;
	private String silsa_reason;
	private int silsa_reg_code;
	private LocalDateTime silsa_reg_date;
	private int silsa_after_amount;
	
	private String product_name;
}
