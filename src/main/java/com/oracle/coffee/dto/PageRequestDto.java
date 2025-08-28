package com.oracle.coffee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {
	@Builder.Default
	private int page = 1;

	@Builder.Default
	private int size = 10;
	  
	private int start;
	private int end;
	
	private String keyword;
}
