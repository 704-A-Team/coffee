package com.oracle.coffee.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.ProvideDto;
import com.oracle.coffee.dto.WonProductPriceDto;
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
	
	@GetMapping("/wonProductPriceInForm")
	public String wonProductPriceInForm(WonProductPriceDto wonProductPriceDto, Model model) {
		log.info("SWProductPriceController wonProductPriceInForm start...");
		
		List<ProductDto> wonProductAllList = swProductService.wonProductAllList();
		
		model.addAttribute("wonProductAllList", wonProductAllList);
		
		return "sw/wonPrice/inForm";
	}
	
	@PostMapping("/wonProductPriceSave")
	public String wonProductPriceSave(WonProductPriceDto wonProductPriceDto, Model model) {
		log.info("SWProductPr	iceController wonProductPriceSave start...");

		System.out.println("SWProductPriceController wonProductPriceSave wonProductPriceDto : " + wonProductPriceDto);
		
		try {
			swProductPriceService.wonProductPriceSave(wonProductPriceDto);
			return "redirect:/sw/wonProductList";
		} catch (Exception e) {
			String errorMsg;
	        if (e.getMessage().contains("ORA-00001")) {
	            errorMsg = "제품 가격은 하루에 한번만 변경가능합니다.";
	        } else {
	            errorMsg = "가격 등록 중 오류 발생: " + e.getMessage();
	        }
	        List<ProductDto> wonProductAllList = swProductService.wonProductAllList();
	        model.addAttribute("wonProductAllList", wonProductAllList);
		    model.addAttribute("errorMsg", errorMsg);
		    
		    return "sw/wonPrice/inForm";
		}
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
}
