package com.oracle.oBootTodoApi01.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;

@Data
public class PageResponseDTO<E> {
	
	private List<E> 		dtoList;     // tbl_todo 리스트
	private List<Integer> 	pageNumList;
	private PageRequestDTO  pageRequestDTO;
	private int totalCount, prevPage, nextPage, totalPage, current;
	private boolean prev, next;
	
	@Builder(builderMethodName = "withAll")
	PageResponseDTO(List<E> dtoList, PageRequestDTO  pageRequestDTO, int totalCount) {
		this.dtoList = dtoList;
		this.pageRequestDTO = pageRequestDTO;
		this.totalCount = totalCount;
		
		System.out.println("PageResponseDTO withAll-> "+pageRequestDTO);
		
		// Page Setting
		int end = (int) ((Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10);
		int start = end - 9;
		int last = (int) (Math.ceil(totalCount / (double)pageRequestDTO.getSize()));
		System.out.println("Before end-> "+end);
		end = end > last ? last : end;
		
		System.out.println("start-> "+start);
		System.out.println("After end-> "+end);
		System.out.println("last-> "+last);
		
		this.prev = start > 1;
		this.next = totalCount > end * pageRequestDTO.getSize();
		
		this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
		
		System.out.println("prev-> "+prev);
		System.out.println("next-> "+next);
		System.out.println("pageNumList-> "+pageNumList);
		
		if(prev) {
			this.prevPage = start - 1;
		}
		
		if(next) {
			this.nextPage = end + 1;
		}
		
		this.totalPage = this.pageNumList.size();
		
		this.current = pageRequestDTO.getPage();
		
	}
	
}
