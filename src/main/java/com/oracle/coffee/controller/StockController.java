package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.stock.MagamPageDto;
import com.oracle.coffee.dto.stock.MonthMagamDto;
import com.oracle.coffee.dto.stock.SilsaDto;
import com.oracle.coffee.dto.stock.StockDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.StockService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class StockController {

	private final StockService stockService;
	
	// 재고 현황 페이지
	@GetMapping("/list")
	public String listPage(PageRequestDto page, Model model) {
		PageRespDto<StockDto, Paging> listData = stockService.getStockList(page);
		boolean isClosed = stockService.isClosedMagam();
		boolean isClosedToday = stockService.isClosedToday();
		
		model.addAttribute("inventoryList", listData.getList());
    	model.addAttribute("page", listData.getPage());
    	model.addAttribute("isClosed", isClosed);
    	model.addAttribute("isClosedToday", isClosedToday);
    	
		return "stock/list";
	}
	
	// 수동 일마감
	@GetMapping("/close")
	public String closeTodayMagam() {
		try {
			stockService.closeTodayMagam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/inventory/list";
	}
	
	// 일마감 취소
	@GetMapping("/cancel")
	public String cancelTodayMagam() {
		try {
			stockService.cancelTodayMagam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/inventory/list";
	}
	
	// 월마감 내역 페이지
	@GetMapping("/magam")
	public String magamHistroyPage(PageRequestDto page, Model model) {
		PageRespDto<MonthMagamDto, Paging> magams = stockService.getMonthMagams(page);
		
		model.addAttribute("monthMagams", magams.getList());
		model.addAttribute("page", magams.getPage());
		return "stock/magam";
	}
	
	// 월마감 월별 페이지
	@GetMapping("/magam/{yrmo}/{status}")
	public String magamdetailPage(
			@PathVariable("yrmo") String yrmo,
			@PathVariable("status") int status,
			PageRequestDto page,
			Model model
			) {
		
		MagamPageDto magamPage = MagamPageDto.builder()
											 .yrmo(yrmo)
											 .status(status)
											 .build();
		PageRespDto<MonthMagamDto, Paging> magams = stockService.getMonthMagamProducts(page, magamPage);
		
		model.addAttribute("yrmo", yrmo);
		model.addAttribute("status", status);
		
		model.addAttribute("magamProducts", magams.getList());
		model.addAttribute("page", magams.getPage());
		return "stock/magam_monthly";
	}
	
	// 재고 조정(실사)페이지
	@GetMapping("/silsa")
	public String silsaPage(Model model) {
		List<StockDto> products = stockService.getAllStock();
		boolean isClosedToday = stockService.isClosedToday();

		model.addAttribute("products", products);
    	model.addAttribute("isClosed", isClosedToday);
		
		return "stock/silsa";
	}
	
	// 실사 저장
	@PostMapping("/silsa")
	public String silsaSave(@RequestBody List<SilsaDto> silsaList) {
		// 오늘자 실사가 있다면 예외처리
		
		try {
			// 등록자
			int loginEmpCode = 2003;
			stockService.saveSilsa(silsaList, loginEmpCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/inventory/list";
	}
	
}
