package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/sw")
public class SWController {
	private final ProductService 	productService;
	
	@GetMapping("/wonProductInForm")
	public String wonProductInForm() {
		System.out.println("SWController wonProductInForm Strart...");
		
		return "sw/product/wonInForm";
	}
	
	@PostMapping("/wonProductSave")
	public String wonProductSave(ProductDto productDto) {
		System.out.println("SWController wonProductSave Strart...");
		
		int wonProduct_code = productService.wonProductSave(productDto);
		log.info("Save wonProduct_code : ", wonProduct_code);
		
		return "redirect:/sw/wonProductList";
	}
	
	@GetMapping("/wonProductList")
	public String wonProductListPage(ProductDto productDto, Model model) {
		System.out.println("SWController wonProductListPage Strart...");
		
		int totalWonCount = productService.totalWonProduct();
		System.out.println("SWController wonProductListPage totalCount : " + totalWonCount);
		
		Paging page = new Paging(totalWonCount, productDto.getCurrentPage());
		
		productDto.setStart(page.getStart());
		productDto.setEnd(page.getEnd()); 
		System.out.println("SWController wonProductListPage productDto : "+productDto);
		
		productDto.setProduct_type(0);
		List<ProductDto> wonProductDtoList = productService.productList(productDto);
		System.out.println("SWController wonProductListPage wonProductDtoList.size() : " + wonProductDtoList.size());
		
		model.addAttribute("totalWonCount", totalWonCount);
		model.addAttribute("wonProductDtoList", wonProductDtoList);
		model.addAttribute("page", page);
		
		return "sw/product/wonList";
	}
	
	@GetMapping("/wonProductDetail")
	public String wonProductDetailPage(@RequestParam("product_code") int product_code, Model model) {
		System.out.println("SWController wonProductDetailPage Strart...");
		
		ProductDto wonProductDetail = productService.wonProductDetail(product_code);
		
		model.addAttribute("wonProductDetail", wonProductDetail);
		
		return "sw/product/wonDetailList";
	}
	
	@GetMapping("/wonProductModifyForm")
	public String wonProductModifyForm(@RequestParam("product_code") int product_code, Model model) {
		System.out.println("SWController wonProductModifyForm Strart...");
		
		ProductDto wonProductDetail = productService.wonProductDetail(product_code);
		
		model.addAttribute("wonProductDetail", wonProductDetail);
		
		return "sw/product/wonModify";
	}
	
	@PostMapping("/wonProductModify")
	public String wonProductModify(ProductDto productDto) {
		System.out.println("SWController wonProductModify Strart...");
		
		int wonProduct_code = productService.wonProductModify(productDto);
		
		return "redirect:/sw/wonProductList";
	}
	
	@PostMapping("/wonProductDelete")
	public String wonProductDelete(@RequestParam("product_code") int product_code) {
		System.out.println("SWController wonProductDelete Strart...");
		
		productService.wonProductDelete(product_code);
		
		return "redirect:/sw/wonProductList";
	}
	
	
}




