package com.oracle.coffee.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagamPageDto {
	private int start;
	private int end;
	private String yrmo;
	private int status;
}
