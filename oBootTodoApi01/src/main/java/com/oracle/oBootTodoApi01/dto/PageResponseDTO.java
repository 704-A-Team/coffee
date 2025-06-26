package com.oracle.oBootTodoApi01.dto;

import java.util.List;


import lombok.Builder;
import lombok.Data;

@Data
public class PageResponseDTO<E> {
	
	private List<E> 		dtoList;     // tbl_todo 리스트
	private List<Integer> 	pageNumList;
	private PageRequestDTO  pageRequestDTO;
	private int totalCount, prevPage, nextPage, totalPage, current;
	
	@Builder(builderMethodName = "withAll")
	PageResponseDTO(List<E> dtoList, PageRequestDTO  pageRequestDTO, int totalCount) {
		this.dtoList = dtoList;
		this.pageRequestDTO = pageRequestDTO;
		this.totalCount = totalCount;
	}
	
}
