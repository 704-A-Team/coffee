package com.oracle.coffee.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.dto.orders.OrdersRefuseDto;
import com.oracle.coffee.service.ClientService;
import com.oracle.coffee.service.OrdersService;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.StockService;

import lombok.RequiredArgsConstructor;

////////////////////////////////////////////////////////////
////////////////////// 수주 (가맹점 발주) //////////////////////
////////////////////////////////////////////////////////////

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersController {
	
	private final OrdersService ordersService;
	private final ClientService clientService;
	private final StockService stockService;
	
	// 새로운 수주서 페이지
	@GetMapping("/new")
	public String newOrderPage(@AuthenticationPrincipal AccountDto login, Model model) {
		// 본사 직원 생성 불가
		// 로그인한 가맹점 정보 조회
		int clientCode = login.getClient_code();
		ClientDto client = clientService.getSingleClient(clientCode);
		model.addAttribute("client", client);

		// 판매가능한 제품 목록 조회
		List<OrdersProductDto> products = ordersService.getProducts();
		model.addAttribute("products", products);

		model.addAttribute("nowDate", LocalDate.now());
		model.addAttribute("isFixedPage", false);
		return "order/form";
	}
	
	// 리스트 조회
	@GetMapping("/list")
	public String listPage(@AuthenticationPrincipal AccountDto login, PageRequestDto page, Model model) {
		// 로그인 정보 조회
		boolean isEmp = login.getClient_code() == 0 ? true : false;

		// 본사/가맹점에 따라 리스트 조회
		PageRespDto<OrdersListDto, Paging> respData = null;
		if (isEmp) {	// 본사 직원이 조회
			respData = ordersService.list(page);
		}
		else {	// 가맹점이 조회
			int clientCode = login.getClient_code();
			respData = ordersService.list(page, clientCode);
		}

		model.addAttribute("orders", respData.getList());
		model.addAttribute("page", respData.getPage());
		return "order/list";
	}
	
	// 수주 상세보기 페이지
	@GetMapping("/{order_code}")
	public String detailPage(@AuthenticationPrincipal AccountDto login, @PathVariable("order_code") int orderCode, Model model) {
		// 수주 조회
		OrdersDto order = ordersService.get(orderCode);
		if (order == null) {}	// 404 예외처리

		// 로그인 정보 조회
		boolean isEmp = login.getClient_code() == 0 ? true : false;
		boolean isManagerEmp = login.getRoles().equals("ROLE_MANAGER") ? true : false;
		
		// 수주 가맹점 조회
		int clientCode = order.getOrders_client_code();
		ClientDto client = clientService.getSingleClient(clientCode);
		
		// 마감 상태 조회
		boolean isClosedMagam = stockService.isClosedMagam();
		
		model.addAttribute("isEmp", isEmp);
		model.addAttribute("isManagerEmp", isManagerEmp);
		model.addAttribute("client", client);
		model.addAttribute("order", order);
		model.addAttribute("isFixedPage", true);
		model.addAttribute("isClosedMagam", isClosedMagam);
		
		return "order/form";
	}
	
	// 수주 수정 페이지
	@GetMapping("/modify/{order_code}")
	public String modifyPage(@PathVariable("order_code") int orderCode, @AuthenticationPrincipal AccountDto login, Model model) {
		// 수주 조회
		OrdersDto order = ordersService.get(orderCode);
		if (order == null) {}	// 404 예외처리
		
		// 수주 가맹점 조회
		int clientCode = order.getOrders_client_code();
		ClientDto client = clientService.getSingleClient(clientCode);
		
		// 판매가능한 제품 목록 조회
		List<OrdersProductDto> products = ordersService.getProducts();

		// model.addAttrubyte("loginUser", loginUser);
		model.addAttribute("client", client);
		model.addAttribute("order", order);
		model.addAttribute("products", products);
		
		model.addAttribute("isFixedPage", false);
		return "order/form";
	}
	
	// 수주 저장(생성+업데이트): 요청 단계까지만 가능
	@PostMapping("/save")
	public String save(OrdersDto order) {
		// 권한 조회
		// 임시저장/요청 상태 아니면 exception 처리
		
		// 수주 저장
		int orderCode = ordersService.upsertInformation(order);
		return "redirect:/order/" + orderCode;
	}

	// 수주 요청
	@GetMapping("/request/{order_code}")
	public String register(@PathVariable("order_code") int orderCode) {
		// 수주 요청 상태로 변경
		ordersService.request(orderCode);
		// 자동 승인 진행
		ordersService.autoApprove(orderCode);
		return "redirect:/order/" + orderCode;
	}
	
	// 수주 임시저장 삭제
	@GetMapping("/del/{order_code}")
	public String delete(@PathVariable("order_code") int orderCode) {
		ordersService.delete(orderCode);
		return "redirect:/order/list";
	}
	
	// 수주 취소(반려): 요청 상태인 경우만 가능
	@PostMapping("/cancel")
	@ResponseBody
	public ResponseEntity cancel(@RequestBody OrdersRefuseDto refuse, @AuthenticationPrincipal AccountDto login) {
		if (refuse.getReason() != null) {
			// 반려한 본사 직원 정보 조회
			int loginEmpCode = login.getEmp_code();
			refuse.setOrder_perm_code(loginEmpCode);
		}
		ordersService.refuseOrCancel(refuse);
		
		return ResponseEntity.ok().build();
	}

	// 수주 승인: 요청 상태인 경우만 가능
	@GetMapping("/approve/{order_code}")
	@ResponseBody
	public List<OrdersDetailDto> approve(@PathVariable("order_code") int orderCode, @AuthenticationPrincipal AccountDto login) {
		// 로그인한 본사 직원
		int loginEmpCode = login.getEmp_code();
		List<OrdersDetailDto> disableds = ordersService.approve(loginEmpCode, orderCode);
		
		return disableds;
	}
}
