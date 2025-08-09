package com.oracle.coffee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.service.Paging;
import com.oracle.coffee.service.SWProductService;
import com.oracle.coffee.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/sw")
public class SWProductController {
	private final SWProductService 	productService;
	private final CustomFileUtil	fileUtil;
	
	@GetMapping("/wonProductInForm")
	public String wonProductInForm() {
		System.out.println("SWProductController wonProductInForm Strart...");
		
		return "sw/product/wonInForm";
	}
	
	@PostMapping("/wonProductSave")
	public String wonProductSave(ProductDto productDto, RedirectAttributes redirectAttrs) {
		System.out.println("SWProductController wonProductSave Strart...");
		
		List<MultipartFile> file = productDto.getFiles();
		List<String> uploadFileNames = fileUtil.saveFiles(file);
		productDto.setUploadFileNames(uploadFileNames);
		productDto.setProduct_type(0);
		
		int wonProduct_code = productService.wonProductSave(productDto);
		log.info("Save wonProduct_code : ", wonProduct_code);
		
		redirectAttrs.addAttribute("currentPage", productDto.getCurrentPage());
	    redirectAttrs.addAttribute("searchKeyword", productDto.getSearchKeyword());
		
		return "redirect:/sw/wonProductList";
	}
	
	@GetMapping("/wonProductList")
	public String wonProductListPage(ProductDto productDto, Model model) {
		System.out.println("SWProductController wonProductListPage Strart...");
		
		int totalWonCount = productService.totalWonProduct(productDto);
		System.out.println("SWProductController wonProductListPage totalCount : " + totalWonCount);
		
		Paging page = new Paging(totalWonCount, productDto.getCurrentPage());
		
		productDto.setStart(page.getStart());
		productDto.setEnd(page.getEnd()); 
		System.out.println("SWProductController wonProductListPage productDto : "+productDto);
		
		productDto.setProduct_type(0);
		List<ProductDto> wonProductDtoList = productService.productList(productDto);
		System.out.println("SWProductController wonProductListPage wonProductDtoList.size() : " + wonProductDtoList.size());
		
		model.addAttribute("totalWonCount", totalWonCount);
		model.addAttribute("wonProductDtoList", wonProductDtoList);
		model.addAttribute("page", page);
		
		return "sw/product/wonList";
	}
	
	@GetMapping("/wonProductDetail")
	public String wonProductDetailPage(@RequestParam("product_code") int product_code, Model model) {
		System.out.println("SWProductController wonProductDetailPage Strart...");
		
		ProductDto wonProductDetail = productService.wonProductDetail(product_code);
		System.out.println("SWProductController wonProductDetailPage wonProductDetail : " + wonProductDetail);
		
		model.addAttribute("wonProductDetail", wonProductDetail);
		
		return "sw/product/wonDetailList";
	}
	
	@GetMapping("/wonProductModifyForm")
	public String wonProductModifyForm(@RequestParam("product_code") int product_code, Model model) {
		System.out.println("SWProductController wonProductModifyForm Strart...");
		
		ProductDto wonProductDetail = productService.wonProductDetail(product_code);
		System.out.println("SWProductController wonProductModifyForm wonProductDetail : " + wonProductDetail);
		
		model.addAttribute("wonProductDetail", wonProductDetail);
		
		return "sw/product/wonModify";
	}
	
	@PostMapping("/wonProductModify")
    public String wonProductModify(ProductDto productDto) {
        List<String> existingFileNames = productDto.getUploadFileNames();
        if (existingFileNames == null) existingFileNames = List.of();

        List<String> newUploadFileNames = fileUtil.saveFiles(productDto.getFiles());

        // 병합 처리
        existingFileNames = new ArrayList<>(existingFileNames);
        existingFileNames.addAll(newUploadFileNames);
        productDto.setUploadFileNames(existingFileNames);

        int wonProduct_code = productService.wonProductModify(productDto);
        log.info("Modify wonProduct_code : {}", wonProduct_code);

        return "redirect:/sw/wonProductList";
    }

	
	@PostMapping("/wonProductDelete")
	public String wonProductDelete(@RequestParam("product_code") int product_code) {
		System.out.println("SWProductController wonProductDelete Strart...");
		
		productService.wonProductDelete(product_code);
		
		return "redirect:/sw/wonProductList";
	}
	
	
	
}




