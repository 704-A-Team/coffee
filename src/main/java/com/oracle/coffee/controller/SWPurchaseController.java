package com.oracle.coffee.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.coffee.VO.PurchaseForm;
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

	private final SWPurchaseService swPurchaseService;
	private final SWClientService swClientService;
	private final SWProductService swProductService;
	private final ProvideService provideService;

	@GetMapping("/purchaseInForm")
	public String purchaseInForm(Model model) {
		System.out.println("SWPurchaseController purchaseInForm start...");

		int product_type = 0;
		List<ProductDto> productIsList = swProductService.productIsList(product_type);

		SWClientDto swclientDto = new SWClientDto();
		swclientDto.setClient_type(2);
		swclientDto.setClient_status(0);
		List<SWClientDto> clientIsList = swClientService.clientIsList(swclientDto);

		model.addAttribute("productIsList", productIsList);
		model.addAttribute("clientIsList", clientIsList);

		return "sw/purchase/inForm";
	}

	@GetMapping("getClientsByProduct")
	@ResponseBody
	public List<SWClientDto> getClientsByProduct(@RequestParam("product_code") int product_code) {
		System.out.println("SWPurchaseController getClientsByProduct start...");
		System.out.println("product_code : " + product_code);

		return swClientService.getClientsByProduct(product_code);
	}

	@GetMapping("getProductsByClient")
	@ResponseBody
	public List<ProvideDto> getProductsByClient(@RequestParam("provide_client_code") int client_code) {
		System.out.println("SWPurchaseController getProductsByClient start...");
		System.out.println("client_code : " + client_code);

		return provideService.getProductsByClient(client_code);
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
	public String purchaseSave(PurchaseForm form) {
		log.info("SWPurchaseController purchaseSave start...");
		
		int n = form.getProduct_won_code().size();
	    List<PurchaseDto> list = new ArrayList<>(n);
	    
	    for (int i = 0; i < n; i++) {
	        PurchaseDto dto = new PurchaseDto();
	        dto.setPurchase_client_code(form.getPurchase_client_code().get(i));
	        dto.setPurchase_status(1);
	        dto.setProduct_won_code(form.getProduct_won_code().get(i));
	        dto.setPurchase_amount(form.getPurchase_amount().get(i));
	        dto.setPurchase_danga(form.getPurchase_danga().get(i));

	        list.add(dto);
	    }

		swPurchaseService.purchaseSave(list);

		return "redirect:/sw/purchaseList";
	}

	@GetMapping("/purchaseList")
	public String purchaseListPage(PurchaseDto purchaseDto, Model model) {
		System.out.println("SWPurchaseController purchaseListPage Strart...");

		int totalPurchaseCnt = swPurchaseService.totalPurchaseCnt(purchaseDto);
		System.out.println("SWPurchaseController purchaseListPage totalPurchaseCnt : " + totalPurchaseCnt);

		Paging page = new Paging(totalPurchaseCnt, purchaseDto.getCurrentPage());

		purchaseDto.setStart(page.getStart());
		purchaseDto.setEnd(page.getEnd());
		System.out.println("SWPurchaseController purchaseListPage purchaseDto : " + purchaseDto);

		List<PurchaseDto> purchaseList = swPurchaseService.purchaseList(purchaseDto);
		System.out.println("SWPurchaseController purchaseListPage purchaseList : " + purchaseList);

		model.addAttribute("totalPurchaseCnt", totalPurchaseCnt);
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("page", page);

		return "sw/purchase/list";
	}

	@GetMapping("/purchaseDetail")
	public String purchaseDetailPage(@RequestParam("purchase_code") int purchase_code, Model model) {
		System.out.println("SWPurchaseController purchaseDetailPage Strart...");

		List<PurchaseDto> purchaseDetailList = swPurchaseService.purchaseDetailList(purchase_code);

		model.addAttribute("purchaseDetailList", purchaseDetailList);
		
		boolean isApprovable = (purchaseDetailList != null && !purchaseDetailList.isEmpty())
                && "1".equals(String.valueOf(purchaseDetailList.get(0).getPurchase_status()));
		model.addAttribute("isApprovable", isApprovable);

		return "sw/purchase/detailList";
	}

	@PostMapping("/purchaseApprove")
	public String purchaseApprove(@RequestParam("purchase_code") int purchase_code) {
		System.out.println("SWPurchaseController purchaseApprove Strart...");

		swPurchaseService.purchaseApprove(purchase_code);

		return "redirect:/sw/purchaseList";

	}

	@PostMapping("/purchaseRefuse")
	public String purchaseRefuse(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseController purchaseRefuse Strart...");

		purchaseDto.setPurchase_status(3);
		swPurchaseService.purchaseRefuse(purchaseDto);

		return "redirect:/sw/purchaseList";
	}

}
