package com.oracle.coffee.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.coffee.dto.AccountDto;
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
		boolean isClosed = stockService.isClosedMonth();
		boolean isClosedToday = stockService.isClosedToday();
		
		model.addAttribute("inventoryList", listData.getList());
    	model.addAttribute("page", listData.getPage());
    	model.addAttribute("isClosed", isClosed);
    	model.addAttribute("isClosedToday", isClosedToday);
    	
		return "stock/list";
	}
	
	// 수동 월마감
	@GetMapping("/close/mm")
	public String closeMonthMagam() {
		try {
			stockService.closeMonthMagam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/inventory/list";
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
		boolean isClosed = stockService.isClosedMonth();
		
		model.addAttribute("monthMagams", magams.getList());
		model.addAttribute("page", magams.getPage());
		model.addAttribute("isClosed", isClosed);
    	
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
		String now = DateTimeFormatter.ofPattern("YYYY년 MM월 dd일").format(LocalDate.now());
		
		// 선택 가능한 재고 목록
		List<StockDto> products = stockService.getAllStock();
		
		boolean isClosed = stockService.isClosedMonth();
		boolean isClosedToday = stockService.isClosedToday();
		
		// 오늘 저장된 실사내역
		List<SilsaDto> silsas = stockService.getTodaySilsa();

		model.addAttribute("now", now);
		model.addAttribute("products", products);
    	model.addAttribute("isClosed", isClosed);
    	model.addAttribute("isClosedToday", isClosedToday);
    	model.addAttribute("silsas", silsas);
    	
		return "stock/silsa";
	}
	
	// 실사 저장
	@PostMapping("/silsa")
	public String silsaSave(@RequestBody List<SilsaDto> silsaList, @AuthenticationPrincipal AccountDto login) {
		try {
			// 등록자
			int empCode = login.getEmp_code();
			stockService.saveSilsa(silsaList, empCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/inventory/list";
	}
	
	@GetMapping("/silsa/list")
	public String silsaListPage(PageRequestDto page, Model model) {
		PageRespDto<SilsaDto, Paging> silsas = stockService.getSilsaList(page);

		model.addAttribute("silsas", silsas.getList());
    	model.addAttribute("page", silsas.getPage());
		return "stock/silsa_list";
	}

}
