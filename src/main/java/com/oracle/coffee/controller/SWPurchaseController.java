package com.oracle.coffee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.coffee.VO.PurchaseForm;
import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.ProvideDto;
import com.oracle.coffee.dto.PurchaseDto;
import com.oracle.coffee.dto.SWClientDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.ProvideService;
import com.oracle.coffee.service.SWClientService;
import com.oracle.coffee.service.SWProductService;
import com.oracle.coffee.service.SWPurchaseService;
import com.oracle.coffee.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/sw")
public class SWPurchaseController {

	private final SWPurchaseService 	swPurchaseService;
	private final SWClientService 		swClientService;
	private final SWProductService 		swProductService;
	private final ProvideService 		provideService;
	private final StockService			stockService; 

	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_MANAGER')")
	@GetMapping("/purchaseInForm")
	public String purchaseInForm(Model model) {
		System.out.println("SWPurchaseController purchaseInForm start...");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println("controller wonProductList authentication : "+ authentication);
	     
	    AccountDto account = (AccountDto) authentication.getPrincipal();
	    int emp_code = account.getEmp_code();
	    System.out.println("emp_code : " + emp_code);
		
		int magamStatus = stockService.magamCheck();
		
		int product_type = 0;
		List<ProductDto> productIsList = swProductService.productIsList(product_type);

		SWClientDto swclientDto = new SWClientDto();
		swclientDto.setClient_type(2);
		swclientDto.setClient_status(0);
		List<SWClientDto> clientIsList = swClientService.clientIsList(swclientDto);
		
		model.addAttribute("emp_reg_code", emp_code);
		model.addAttribute("productIsList", productIsList);
		model.addAttribute("clientIsList", clientIsList);
		model.addAttribute("magamStatus", magamStatus);
		
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
	        dto.setPurchase_reg_code(form.getPurchase_reg_code());
	        
	        list.add(dto);
	    }
	    
	    System.out.println("list : " + list);
		swPurchaseService.purchaseSave(list);

		return "redirect:/sw/purchaseList";
	}

	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_MANAGER','ROLE_CLIENT')")
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

	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_MANAGER','ROLE_CLIENT')")
	@GetMapping("/purchaseDetail")
	public String purchaseDetailPage(@RequestParam("purchase_code") int purchase_code, Model model) {
		System.out.println("SWPurchaseController purchaseDetailPage Strart...");
		
		int magamStatus = stockService.magamCheck();
		
		List<PurchaseDto> purchaseDetailList = swPurchaseService.purchaseDetailList(purchase_code);

		model.addAttribute("purchaseDetailList", purchaseDetailList);
		
		boolean isApprovable = (purchaseDetailList != null && !purchaseDetailList.isEmpty())
                && "1".equals(String.valueOf(purchaseDetailList.get(0).getPurchase_status()));
		model.addAttribute("isApprovable", isApprovable);
		model.addAttribute("magamStatus", magamStatus);

		return "sw/purchase/detailList";
	}

	@PostMapping("/purchaseApprove")
	public String purchaseApprove(PurchaseDto purchaseDto, @AuthenticationPrincipal AccountDto account) {
		System.out.println("SWPurchaseController purchaseApprove Strart...");
		
		purchaseDto.setPurchase_perm_code(account.getEmp_code());
		swPurchaseService.purchaseApprove(purchaseDto);

		return "redirect:/sw/purchaseList";

	}

	@PostMapping("/purchaseRefuse")
	public String purchaseRefuse(PurchaseDto purchaseDto, @AuthenticationPrincipal AccountDto account) {
		System.out.println("SWPurchaseController purchaseRefuse Strart...");

		purchaseDto.setPurchase_status(3);
		purchaseDto.setPurchase_perm_code(account.getEmp_code());
		swPurchaseService.purchaseRefuse(purchaseDto);

		return "redirect:/sw/purchaseList";
	}

}
