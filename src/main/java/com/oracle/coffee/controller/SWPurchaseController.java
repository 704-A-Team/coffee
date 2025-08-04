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
import com.oracle.coffee.dto.PurchaseDto;
import com.oracle.coffee.dto.SWClientDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.ProvideService;
import com.oracle.coffee.service.SWClientService;
import com.oracle.coffee.service.SWProductService;
import com.oracle.coffee.service.SWPurchaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/sw")
public class SWPurchaseController {
	
	private final SWPurchaseService		swPurchaseService;
	private final SWClientService		swClientService;
	private final SWProductService		swProductService;
	private final ProvideService		provideService;
	
	@GetMapping("/purchaseInForm")
	public String purchaseInForm(Model model) {
		System.out.println("SWPurchaseController purchaseInForm start...");
		
		int product_type = 0;
		List<ProductDto> productIsList = swProductService.productIsList(product_type);
		
		
		
		model.addAttribute("productIsList", productIsList);
		
		return "sw/purchase/inForm";
	}
	
	@GetMapping("getClientsByProduct")
	@ResponseBody
	public List<SWClientDto> getClientsByProduct(@RequestParam("product_code") int product_code){
		System.out.println("SWPurchaseController getClientsByProduct start...");
		System.out.println("product_code : " + product_code);
		
		return swClientService.getClientsByProduct(product_code);
	}
	
	@GetMapping("/getProvideInfo")
	@ResponseBody
	public ProvideDto getProvideInfo(ProvideDto provideDto) {
		System.out.println("SWPurchaseController getProvideInfo start...");
		
		ProvideDto result = provideService.getProvideInfo(provideDto);
		System.out.println("SWPurchaseController getProvideInfo result : " + result);
				
	    return result;
	}
	
	@PostMapping("/purchaseSave")
	public String purchaseSave(PurchaseDto purchaseDto) {
		log.info("SWPurchaseController purchaseSave start...");
		
		purchaseDto.setPurchase_status(1);
		int purchase_code = swPurchaseService.purchaseSave(purchaseDto);
		System.out.println("save purchase_code : " + purchase_code);
		
		return "sw/purchase/list";
	}
	
	@GetMapping("/purchaseList")
	public String purchaseListPage(PurchaseDto purchaseDto, Model model) {
		System.out.println("SWPurchaseController purchaseListPage Strart...");
		
		int totalPurchaseCnt = swPurchaseService.totalPurchaseCnt(purchaseDto);
		System.out.println("SWPurchaseController purchaseListPage totalPurchaseCnt : " + totalPurchaseCnt);
		
		Paging page = new Paging(totalPurchaseCnt, purchaseDto.getCurrentPage());
		
		purchaseDto.setStart(page.getStart());
		purchaseDto.setEnd(page.getEnd()); 
		System.out.println("SWPurchaseController purchaseListPage purchaseDto : "+ purchaseDto);
		
		List<PurchaseDto> purchaseList = swPurchaseService.purchaseList(purchaseDto);
		System.out.println("SWPurchaseController purchaseListPage purchaseList : "+ purchaseList);
		
		model.addAttribute("totalPurchaseCnt", totalPurchaseCnt);
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("page", page);
		
		return "sw/purchase/list";
	}
	
	@GetMapping("/purchaseDetail")
	public String purchaseDetailPage(@RequestParam("purchase_code") int purchase_code, Model model) {
		System.out.println("SWPurchaseController purchaseDetailPage Strart...");
		
		PurchaseDto purchaseDetail = swPurchaseService.purchaseDetail(purchase_code);
		
		model.addAttribute("purchaseDetail", purchaseDetail);
		
		return "sw/purchase/detailList";
	}
	
}
