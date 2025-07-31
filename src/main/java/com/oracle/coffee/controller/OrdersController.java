package com.oracle.coffee.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.servie.OrdersService;

import lombok.RequiredArgsConstructor;

////////////////////////////////////////////////////////////
////////////////////// 수주 (가맹점 발주) //////////////////////
////////////////////////////////////////////////////////////

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersController {
	
	private final OrdersService ordersService;
	
	// 새로운 수주서 페이지
	@GetMapping("/new")
	public String newOrderPage(Model model) {
		// 로그인한 가맹점 정보 조회
		// model.addAttribute("client", client);
		model.addAttribute("client_code", 3001);

		// 판매가능한 제품 목록 조회
		List<OrdersProductDto> products = ordersService.getProducts();
		model.addAttribute("products", products);

		model.addAttribute("nowDate", LocalDate.now());
		return "order/form";
	}
	
	// 리스트 조회
	@GetMapping("/list")
	public String listPage() {
		// 로그인한 정보 조회
		return "order/list";
	}
	
	// 수주 상세보기
	@GetMapping("/{order_code}")
	public String detailPage(@PathVariable("order_code") int orderCode, Model model) {
		// 수주 조회
		OrdersDto order = ordersService.get(orderCode);
		if (order == null) {}	// 404 예외처리
		
		// 가명점 정보 조회
		 
		// 로그인한 가맹점/본사직원 정보 조회
		// 권한 확인
		
		// 판매가능한 제품 목록 조회
		List<OrdersProductDto> products = ordersService.getProducts();
		
		model.addAttribute("client_code", 3001); 
		// model.addAttribute("client", client);
		// model.addAttrubyte("loginUser", loginUser);
		model.addAttribute("order", order);
		model.addAttribute("products", products);
		return "order/form";
	}
	
	// 수주 저장(생성+업데이트): 요청 단계에서만 가능
	@PostMapping("/save")
	public String save(OrdersDto order) {
		if (order.getOrder_code() != 0) {
			// 권한 조회
			// 임시저장/요청 상태 아니면 exception 처리
		}
		
		// 수주 저장
		int orderCode = ordersService.upsertImpormation(order);
		return "redirect:/order/" + orderCode;
	}

	// 수주 요청
	@PostMapping("/request")
	public String register(OrdersDto order) {
		// 수주 요청 상태로 변경
		int orderCode = ordersService.request(order);
		return "redirect:/order/" + orderCode;
	}
	
	// 수주 승인, 취소(반려): 요청 상태인 경우만 가능
	@PostMapping("/refuse")
	public String refuse() {
		return "";
	}
}
