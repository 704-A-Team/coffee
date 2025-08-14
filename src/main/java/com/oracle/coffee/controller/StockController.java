package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.SilsaDto;
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
		return "redirect:/inventory/list";
	}
	
	// 마감 취소
	@GetMapping("/cancel")
	public String cancelMagam() {
		try {
			stockService.cancelTodayMagam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/inventory/list";
	}
	
	// 마감 내역 페이지
	@GetMapping("/magam")
	public String magamHistroyPage(Model model) {
		return "";
	}
	
	// 재고 조정(실사)페이지
	@GetMapping("/silsa")
	public String silsaPage(Model model) {
		List<StockDto> products = stockService.getAll();
		model.addAttribute("products", products);
		
		return "jh/silsa";
	}
	
	// 실사 저장
	@PostMapping("/silsa")
	public String silsaSave(@RequestBody List<SilsaDto> silsaList) {
		// 오늘자 실사가 있다면 예외처리
		
		// "실사 요청"(마감 상태) -> 마감 취소 -> 실사 저장 -> 재마감
		try {
			stockService.cancelTodayMagam();
			
			// 등록자
			int loginEmpCode = 2003;
			stockService.saveSilsa(silsaList, loginEmpCode);
			
			stockService.closeTodayMagam();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
}
