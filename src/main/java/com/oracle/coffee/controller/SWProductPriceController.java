package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.ProvideDto;
import com.oracle.coffee.dto.WonProductPriceDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.ProvideService;
import com.oracle.coffee.service.SWProductPriceService;
import com.oracle.coffee.service.SWProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/sw")
public class SWProductPriceController {
	private final SWProductPriceService swProductPriceService;
	private final SWProductService		swProductService;
	private final ProvideService		provideService;
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@GetMapping("/wonProductPriceInForm")
	public String wonProductPriceInForm(@RequestParam("product_code") int product_code, Model model) {
		log.info("SWProductPriceController wonProductPriceInForm start...");
		
		ProductDto wonProductDetail = swProductService.wonProductDetail(product_code);
		
		model.addAttribute("wonProductDetail", wonProductDetail);
		
		return "sw/wonPrice/inForm";
	}
	
	@PostMapping("/wonProductPriceSave")
	public String wonProductPriceSave(WonProductPriceDto wonProductPriceDto, Model model, @AuthenticationPrincipal AccountDto account) {
		log.info("SWProductPriceController wonProductPriceSave start...");

		wonProductPriceDto.setPrice_reg_code(account.getEmp_code());
		System.out.println("SWProductPriceController wonProductPriceSave wonProductPriceDto : " + wonProductPriceDto);
		
		swProductPriceService.wonProductPriceSave(wonProductPriceDto);
		
        List<ProductDto> wonProductAllList = swProductService.wonProductAllList();
        model.addAttribute("wonProductAllList", wonProductAllList);
        
        return "redirect:/sw/wonProductList";
	}
	
	//조회용
	@GetMapping("/getProvideByProduct")
	@ResponseBody
	public List<ProvideDto> getProvideByProduct(@RequestParam("product_won_code") int product_code) {
		log.info("getProvideByProduct start...");
		
		List<ProvideDto> getProvideByProduct = provideService.getProvideByProduct(product_code);
		System.out.println("getProvideByProduct : " + getProvideByProduct);
		
		return getProvideByProduct;
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_MANAGER')")
	@GetMapping("/wonProductPriceList")
	public String wonProductPriceList(WonProductPriceDto wonProductPriceDto, Model model) {
		log.info("SWProductPriceController wonProductPriceList start...");
		
		int totalWonProductPriceCnt = swProductPriceService.isPriceCheck(wonProductPriceDto.getProduct_code());
		System.out.println("SWProductPriceController wonProductPriceList totalWonProductPriceCnt : " + totalWonProductPriceCnt);
		
		Paging page = new Paging(totalWonProductPriceCnt, wonProductPriceDto.getCurrentPage());
		
		wonProductPriceDto.setStart(page.getStart());
		wonProductPriceDto.setEnd(page.getEnd());
		System.out.println("SWProductPriceController wonProductPriceList wonProductPriceDto : "+ wonProductPriceDto);
		
		List<WonProductPriceDto> wonProductPriceList = swProductPriceService.wonProductPriceList(wonProductPriceDto);
		System.out.println("SWProductPriceController wonProductPriceList : " + wonProductPriceList);
		
		model.addAttribute("totalWonProductPriceCnt", totalWonProductPriceCnt);
		model.addAttribute("wonProductPriceList", wonProductPriceList);
		model.addAttribute("page", page);
		
		return "sw/wonPrice/list";
	}
}
