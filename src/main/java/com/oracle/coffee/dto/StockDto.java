package com.oracle.coffee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {
	private int product_code;
	private String product_name;
	private int product_unit;
	private int product_type;			// 완제품, 원재료
	private boolean product_isorder;	// 납품여부
	private int real_stock;				// 실재고 수량
	private int usable_stock;		// 가용재고 수량
	
	// join 조회용
	private String unit_contents;		// 단위
	private String type_contents;		// 완제품, 원재료
}
