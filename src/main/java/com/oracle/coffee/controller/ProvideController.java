package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.coffee.dto.SWClientDto;
import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.ProvideDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.ProvideService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/provide")
public class ProvideController {
	private final ProvideService provideService;
	
	@GetMapping("/provideInForm")
	public String wonProductInForm(Model model) {
		System.out.println("ProvideController provideInForm Strart...");
		
		int product_type = 0;
		List<ProductDto> productList = provideService.getProductInfo(product_type);
		int client_type = 2;
		List<SWClientDto> clientList = provideService.getClientInfo(client_type);
		
		model.addAttribute("productList", productList);
		model.addAttribute("clientList", clientList);
		
		return "sw/provide/inForm";
	}
	
	@PostMapping("/provideSave")
	public String provideSave(ProvideDto provideDto) {
		System.out.println("ProvideController provideSave Strart...");
		
		int provide_code = provideService.provideSave(provideDto);
		log.info("Save provide_code : ", provide_code);
		
		return "redirect:/provide/provideList";
	}
	
	@GetMapping("/provideList")
	public String provideListPage(ProvideDto provideDto, Model model) {
		System.out.println("ProvideController provideListPage Strart...");
		
		int totalProvideCount = provideService.totalProvide();
		System.out.println("ProvideController provideListPage totalProvideCount : " + totalProvideCount);
		
		Paging page = new Paging(totalProvideCount, provideDto.getCurrentPage());
		
		provideDto.setStart(page.getStart());
		provideDto.setEnd(page.getEnd()); 
		System.out.println("ProvideController provideListPage provideDto : "+ provideDto);
		
		List<ProvideDto> provideList = provideService.provideList(provideDto);
		System.out.println("ProvideController provideListPage provideList.size() : " + provideList.size());
		
		model.addAttribute("totalProvideCount", totalProvideCount);
		model.addAttribute("provideList", provideList);
		model.addAttribute("page", page);
		
		return "sw/provide/list";
	}
	
	@GetMapping("/provideDetail")
	public String provideDetailPage(@RequestParam("provide_code") int provide_code, Model model) {
		System.out.println("ProvideController provideDetailPage Strart...");
		
		ProvideDto provideDetail = provideService.provideDetail(provide_code);
		
		model.addAttribute("provideDetail", provideDetail);
		
		return "sw/provide/detailList";
	}
	
	@GetMapping("/provideModifyForm")
	public String provideModifyForm(@RequestParam("provide_code") int provide_code, Model model) {
		System.out.println("ProvideController provideModifyForm Strart...");
		
		ProvideDto provideDetail = provideService.provideDetail(provide_code);
		
		int product_type = 0;
		List<ProductDto> productList = provideService.getProductInfo(product_type);
		int client_type = 2;
		List<SWClientDto> clientList = provideService.getClientInfo(client_type);
		
		model.addAttribute("productList", productList);
		model.addAttribute("clientList", clientList);
		model.addAttribute("provideDetail", provideDetail);
		
		return "sw/provide/modify";
	}
	
	@PostMapping("/provideModify")
	public String provideModify(ProvideDto provideDto) {
		System.out.println("ProvideController provideModify Strart...");
		
		int provide_code = provideService.provideModify(provideDto);
		System.out.println("ProvideController provideModify provide_code : " + provide_code);
		
		return "redirect:/provide/provideList";
	}
	
	@PostMapping("/provideDelete")
	public String provideDelete(@RequestParam("provide_code") int provide_code) {
		System.out.println("ProvideController provideDelete Strart...");
		
		provideService.provideDelete(provide_code);
		
		return "redirect:/provide/provideList";
	}
	
	
}
