package com.oracle.coffee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MagamStatusDto {
	private String yrmo;
	private int magam_status;		// 0, 1 (일마감)
	private int magam_perm_code;
}
