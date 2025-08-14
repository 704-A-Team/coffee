package com.oracle.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.StockDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.StockService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class StockController {

	private final StockService stockService;
	
	// 재고 현황 페이지
	@GetMapping("/list")
	public String listPage(PageRequestDto page, Model model) {
		PageRespDto<StockDto, Paging> listData = stockService.list(page);
		boolean isClosedMagam = stockService.isClosedMagam();
		
		model.addAttribute("inventoryList", listData.getList());
    	model.addAttribute("page", listData.getPage());
    	model.addAttribute("isClosed", isClosedMagam);
    	
		return "jh/inventoryList";
	}
	
	// 수동 마감
	@GetMapping("/close")
	public String closeMagam() {
		try {
			stockService.closeTodayMagam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/inventory/close";
	}
	
	// 마감 취소
	// 마감 내역 페이지
}
