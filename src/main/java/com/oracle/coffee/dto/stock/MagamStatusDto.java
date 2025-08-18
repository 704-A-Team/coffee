package com.oracle.coffee.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MagamStatusDto {
	private String yrmo;
	private int magam_status;		// 0, 1(일마감), 2(월마감)
	private int magam_perm_code;
}
