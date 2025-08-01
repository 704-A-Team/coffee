package com.oracle.coffee.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.coffee.dto.km.ProductDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;
import com.oracle.coffee.dto.km.RecipeDTO;
import com.oracle.coffee.dto.km.WanAndRecipeDTO;
import com.oracle.coffee.service.km.Paging;
import com.oracle.coffee.service.km.ProductService;
import com.oracle.coffee.util.km.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/km")
public class ProductController {
	
	private final ProductService productService;
	private final CustomFileUtil fileUtil;
	
	@GetMapping("/productInForm")
	public String productInForm() {
		// 사원코드 : 로그인한 사원(인풋 받지 않는다, 임의로 넣기)
		
		return "productWan/productInForm";
	}
	
	// 페이지/기능
	// productInForm/완제품 등록
	@PostMapping("/wanRegister")
	public String wanRegister(@ModelAttribute ProductDTO 	  productDTO
							, @ModelAttribute ProductPriceDTO priceDTO
							, RedirectAttributes redirectAttributes) {
		// 완제품 등록 -->> 레시피 등록 하고 싶을 때 RedirectAttributes 사용
/*
	    // 로그인한 사원 정보 꺼내기
	    EmployeeDTO loginEmp = (EmployeeDTO) session.getAttribute("loginEmp");

	    if (loginEmp != null) {
	        int empCode = loginEmp.getEmp_no();  // 예: 사원 번호

	        // 등록자 세팅
	        productDTO.setProduct_reg_code(empCode);
	        priceDTO.setProduct_reg_code(empCode);
	    }
*/		
		// 임의 사원 등록
		productDTO.setProduct_reg_code(2004);
		priceDTO.setPrice_reg_code(2004);
		
		// productDTO의 등록일 컬럼("현재시간")을 priceDTO "가격변동시작일, 등록일" 에 저장
		LocalDateTime date = productDTO.getProduct_reg_date();
		String reg_date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		priceDTO.setPrice_reg_date(date);	// 등록일(Date)
		priceDTO.setStart_date(reg_date);	// 가격변동일자(String)
		
		
		// File 저장
		List<MultipartFile> file = productDTO.getFile();
		List<String> uploadFileNames = fileUtil.saveFiles(file);
		productDTO.setUploadFileNames(uploadFileNames);
		log.info("productDTO1->"+productDTO);
		log.info("priceDTO1->"+priceDTO);
	
		
		productService.wanRegister(productDTO,priceDTO);
		
		log.info("productService productDTO3->"+productDTO);
		log.info("productService priceDTO3->"+priceDTO);
		// 완제품 등록 -->> 레시피 등록 (제품 코드 필요, 제품명)
		redirectAttributes.addAttribute("product_code",productDTO.getProduct_code());
		redirectAttributes.addAttribute("product_name",productDTO.getProduct_name());
		redirectAttributes.addAttribute("product_pack",productDTO.getProduct_pack());
		return "redirect:/km/wanRecipeInForm";
	}
	
	// 완제품 생산단위 레시피 등록 폼
	@GetMapping("/wanRecipeInForm")
	public String wanRecipeInForm(@RequestParam("product_code") int product_code ,
								  @RequestParam("product_name") String product_name ,
								  @RequestParam("product_pack") String product_pack ,
								  Model model) {
		// 원재료 드롭다운 박스
		List<ProductDTO> wonList = productService.wonList();
		model.addAttribute("wonList", wonList);
		
		// 제품코드 : redirect로 받은 제품코드
		model.addAttribute("product_code", product_code);
		model.addAttribute("product_name", product_name);
		model.addAttribute("product_pack", product_pack);
		return "productWan/wanRecipeInForm";
	}
	
	// 완제품 레시피에 원재료 수량 저장
	@PostMapping("/wanRecipeSave")
	public String wanRecipeSave(@RequestParam("materialsJson") String materialsJson) {
		// 레시피 등록 폼에 작성된 내용 save
		
		log.info("materialsJson->" + materialsJson);
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			List<RecipeDTO> recipeList = objectMapper.readValue(materialsJson, new TypeReference<List<RecipeDTO>>(){});
			
			for(RecipeDTO recipe : recipeList) {
				productService.wanRecipeSave(recipe);
				log.info("recipeDTO->"+recipe);
			}
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/km/wanList";
	}
	
	
	// 리스트
	@GetMapping("/wanList")
	public String wanList(ProductDTO productDTO, Model model) {
		
		int total = productService.countTotal();
		Paging page = new Paging(total, productDTO.getCurrentPage());
		productDTO.setStart(page.getStart());
		productDTO.setEnd(page.getEnd());
		
		List<ProductDTO> wanProductList = productService.wanList(productDTO);
		model.addAttribute("wanProductList" , wanProductList);
		model.addAttribute("page",page);
		return "productWan/wanList";
	}
	
	// 완제품 상세보기
	@GetMapping("/wanAndRcpDetailInForm")
	public String wanAndRcpDetailInForm(WanAndRecipeDTO wanAndRecipeDTO , Model model) {
		log.info("wanAndRcpDetailInForm 'product_code'->"+wanAndRecipeDTO.getProduct_code());
		
		// 완제품 코드(IN) --> 완제품과 레시피Dto(OUT)  단일 객체 , List 안필요하긴 했었어
		WanAndRecipeDTO wanAndRcpDetailDTO = productService.wanAndRcpDetailInForm(wanAndRecipeDTO.getProduct_code());
		System.out.println("wanAndRcpDetailInForm wanAndRcpDetailDTO->"+wanAndRcpDetailDTO);
		// wanAndRecipeDTO-> date 없다 wanAndRcpDetailDTO 있다
		LocalDateTime date = wanAndRcpDetailDTO.getProduct_reg_date();
		String wan_reg_date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		model.addAttribute("wanAndRcpDetailDTO" , wanAndRcpDetailDTO);
		model.addAttribute("wan_reg_date", wan_reg_date);
		return "productWan/wanAndRcpDetailInForm";
	}
	
	// 상세보기/완제품 수정 폼
	@GetMapping("/wanModifyInForm")
	public String wanModifyInForm(WanAndRecipeDTO wanAndRecipeDTO, Model model) {
		log.info("wanModifyInForm product_code->"+wanAndRecipeDTO.getProduct_code());
		WanAndRecipeDTO wanModifyDTO = productService.wanModifyInForm(wanAndRecipeDTO.getProduct_code());
		
		model.addAttribute("wanModifyDTO" , wanModifyDTO);
		log.info("wanModifyDTO->"+wanModifyDTO);
		return "productWan/wanModifyInForm";
	}
	
	// 완제품 수정
	@PostMapping("/wanModify")
	public String wanModify(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
		log.info("wanModify productDTO->"+productDTO);
		
		
		
		redirectAttributes.addAttribute("product_code",productDTO.getProduct_code());
		return "redirect:/km/wanAndRcpDetailInForm";
	}
	
	

}
