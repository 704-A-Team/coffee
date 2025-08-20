package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.PurchaseDto;
import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.service.OrdersService;
import com.oracle.coffee.service.SWProductService;
import com.oracle.coffee.service.SWPurchaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {
    private final SWProductService 		swProductService;
    private final SWPurchaseService 	swPurchaseService;
    private final OrdersService			ordersService;
	
	@GetMapping("/")
    public String mainPage(Model model) throws JsonProcessingException {
		System.out.println("MainController mainPage start...");
		
		//신규 제품(1달) 목록 가져오기(3)
		List<ProductDto> newProduct = swProductService.getNewProduct();
		
		//최근 1주일 입고 내역 가져오기(5)
		List<PurchaseDto> currentPurchase = swPurchaseService.currentPurchase();
		
		//최근 1주일 출고 내역 가져오기(5)
		List<OrdersDetailDto> currentOrder = ordersService.currentOrder();
		
		//이번달 우수 점포 (수주총액 TOP3)
		List<OrdersDto> excellentClient = ordersService.excellentClient();
		
		//이번달 완제품별 매출액 (TOP5)
		List<OrdersDto> monthTotalPrice = ordersService.monthTotalPrice();
		List<String> productName = monthTotalPrice.stream()
		        								  .map(OrdersDto::getProductName)
		        								  .toList();
		List<Long> MonthTotalPrice = monthTotalPrice.stream()
		        								    .map(OrdersDto::getMonth_total_price)     
		        								    .map(v -> v == null ? 0L : v.longValue())
		        								    .toList();
		// Json 문자열로 변경
		ObjectMapper om = new ObjectMapper();
	    model.addAttribute("topSalesLabels", om.writeValueAsString(productName)); 
	    model.addAttribute("topSalesData",   om.writeValueAsString(MonthTotalPrice));
		
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("currentPurchase", currentPurchase);
		model.addAttribute("currentOrder", currentOrder);
		model.addAttribute("excellentClient", excellentClient);
		model.addAttribute("monthTotalPrice", monthTotalPrice);
		
        return "main";
    }
	
}
