package com.oracle.coffee.dto.stock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthMagamDto {
	private String month_yrmo;
	private int month_magam_status;		// 기초/기말
	private int product_code;
	private int month_magam_amount;		// 월마감 수량
	private LocalDate month_magam_reg_date;
	
	
	// join 조회용
	// product 테이블
	private String product_name;
}
