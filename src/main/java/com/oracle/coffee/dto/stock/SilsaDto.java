package com.oracle.coffee.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SilsaDto {
	private int silsa_product_code;
	private int silsa_status = 4;
	private int silsa_amount;
	private String silsa_reason;
	private int silsa_reg_code;
}
