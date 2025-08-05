package com.oracle.coffee.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageRespDto<T, P> {
	private List<T> list;
	private P page;
}
