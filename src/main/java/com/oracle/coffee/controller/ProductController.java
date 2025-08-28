package com.oracle.coffee.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.oracle.coffee.dto.km.ProductWanDTO;
import com.oracle.coffee.dto.AccountDto;
import com.oracle.coffee.dto.km.ProductPriceDTO;
import com.oracle.coffee.dto.km.RecipeDTO;
import com.oracle.coffee.dto.km.WanAndRecipeDTO;
import com.oracle.coffee.service.km.Paging;
import com.oracle.coffee.service.km.ProductService;
import com.oracle.coffee.util.CustomFileUtil;

import jakarta.servlet.http.HttpServletRequest;
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
	public String wanRegister(@ModelAttribute ProductWanDTO 	  productDTO
							, @ModelAttribute ProductPriceDTO priceDTO
							, RedirectAttributes redirectAttributes
							, @AuthenticationPrincipal AccountDto emp ) {
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
		productDTO.setProduct_reg_code(emp.getEmp_code());
		priceDTO.setPrice_reg_code(emp.getEmp_code());
		
		// productDTO의 등록일 컬럼("현재시간")을 priceDTO "가격변동시작일, 등록일" 에 저장
		LocalDateTime date = productDTO.getProduct_reg_date();
	//	String reg_date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		priceDTO.setPrice_reg_date(date);	// 등록일(Date)
		priceDTO.setStart_date(date);	// 가격변동일자(String)
		
		
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
		List<ProductWanDTO> wonList = productService.wonList();
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
			
			// 1. recipeList 파싱 후 바로 원재료 총합 계산 & 생산단위 추출
			int totalRecipe = 0;
			for(RecipeDTO recipeDTO : recipeList) {
				totalRecipe += recipeDTO.getRecipe_amount(); // 원재료 무게 합산
			}
			
			// 2. 완제품 코드 추출
			int product_wan_code = recipeList.get(0).getProduct_wan_code();
			
			// 3. 제품 테이블에서 생산단위 조회
			int product_pack = productService.findPack(product_wan_code);
		//	int yield = productService.findYield(product_wan_code);
			
			// 4. 기본 중량 계산
			double weight = (double)totalRecipe/product_pack;
		//	double weight = (double)totalRecipe/product_pack/(yield/100.0);
			// 5. 기본 중량 저장
			productService.saveWeight(product_wan_code , weight);
			
			
			for(RecipeDTO recipe : recipeList) {
				if(recipe.getRecipe_amount() > 99999) {
					throw new IllegalArgumentException("레시피 수량은 99999를 넘을 수 없습니다.");
				}
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
	public String wanList(ProductWanDTO productDTO, Model model) {
		
		int total = productService.countTotal();
		System.out.println("total->"+total);
		Paging page = new Paging(total, productDTO.getCurrentPage());
		productDTO.setStart(page.getStart());
		productDTO.setEnd(page.getEnd());
		
		List<ProductWanDTO> wanProductList = productService.wanList(productDTO);
		System.out.println("wanProductList->"+wanProductList);

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
	public String wanModify(ProductWanDTO productDTO, RedirectAttributes redirectAttributes) {
			log.info("wanModify productDTO->"+productDTO);
			
			// 1.수정하고자하는 old ProductWanDTO
			ProductWanDTO oldWanDto = productService.getProductImg(productDTO.getProduct_code());
		    System.out.println("1.ProductController 수정하고자하는 oldWanDto->"+oldWanDto);
		    
		    // 2. old ProductWanDTO내 기존의 파일들 (데이터베이스에 존재하는 파일들 - 수정 과정에서 삭제되었을 수 있음)  
		    List<String> oldFileNames = oldWanDto.getUploadFileNames();
		    System.out.println("2.ProductController empUpdate oldFileNames->"+oldFileNames);
		    
		    // 3. 새로 업로드 해야 하는 파일들  
		    List<MultipartFile> files = productDTO.getFile();
		    System.out.println("3.ProductController empUpdate files.SIZES->"+files.size());
		
		    // 4. 새로 업로드되어서 만들어진 파일 이름들
		    List<String> newUploadFileNames = fileUtil.saveFiles(files);
		    System.out.println("4.ProductController modify newUploadFileNames->"+newUploadFileNames);
		
		    // 5.  화면에서 계속 유지된 파일들 (삭제된 것은 제거)
		    List<String> uploadedFileNames = productDTO.getExistingUploadFileNames();
		    System.out.println("5.ProductController modify uploadedFileNames->"+uploadedFileNames);
		
		    // 6. 유지되는 파일들  + 새로 업로드된 파일 이름들이 저장해야 하는 파일 목록이 됨  
		    if(newUploadFileNames != null && newUploadFileNames.size() > 0) {
		      uploadedFileNames.addAll(newUploadFileNames);
		      System.out.println("6.ProductController modify uploadedFileNames->"+uploadedFileNames);
		
		    }
		    // 7. DB 수정 작업 
		    // productService.modify(productDTO);
		    // 기본적으로 삭제여부는 false
		    // uploaded image Setting
		    productDTO.setProduct_isdel(false);
		    productDTO.setUploadFileNames(uploadedFileNames);
		    System.out.println("8.ProductController modify empUpdate Beforee productDTO->"+productDTO);
		    ProductWanDTO wanModifyDTO = productService.wanModify(productDTO);
		    System.out.println("10.ProductController modify empUpdateDto->"+wanModifyDTO);
		    // ----------------------------------------------------------------
		    // 8. 지워야 하는 파일 목록 찾기 & 삭제 
		    if(oldFileNames != null && oldFileNames.size() > 0){
		
		      //지워야 하는 파일 목록 찾기 
		      //예전 파일들 중에서 지워져야 하는 파일이름들 
		      List<String> removeFiles =  
		    		  oldFileNames
					      .stream()
					      .filter(fileName -> uploadedFileNames.indexOf(fileName) == -1)
					      .collect(Collectors.toList());
		
		      System.out.println("10.ProductController modify removeFiles->"+removeFiles);
		      //실제 파일 삭제 
		      fileUtil.deleteFiles(removeFiles);
			  // ----------------------------------------------------------------
		      System.out.println("11.ProductController modify removeFiles->"+removeFiles);
				
			}
		    
		log.info("productDTO->" + productDTO);
		
		redirectAttributes.addAttribute("product_code",productDTO.getProduct_code());
		return "redirect:/km/wanAndRcpDetailInForm";
	}
	
	// 상세보기/완제품 레시피 수정 폼
	@GetMapping("/recipeModifyInForm")
	public String recipeModifyInForm(ProductWanDTO productWanDTO , Model model) throws JsonProcessingException {
		log.info("recipeModify 들어온 wanAndRecipeDTO->"+productWanDTO);
		WanAndRecipeDTO wanAndRcpDetailDTO = productService.wanAndRcpDetailInForm(productWanDTO.getProduct_code());
		List<ProductWanDTO> wonList = productService.wonList();
		log.info("recipeModify wonList->"+wonList);
		
		log.info("recipeModify 서비스 이후 wanAndRcpDetailDTO->"+wanAndRcpDetailDTO);
		// 컨트롤러에서 JSON 형태로 넘기기
		ObjectMapper mapper = new ObjectMapper();
		model.addAttribute("recipeListJson", mapper.writeValueAsString(wanAndRcpDetailDTO.getRecipeList()));
		log.info("recipeModify 서비스 이후 wanAndRcpDetailDTO.getRecipeList()->"+wanAndRcpDetailDTO.getRecipeList());
		
		model.addAttribute("recipeList", wanAndRcpDetailDTO.getRecipeList()); // 기존 레시피
		model.addAttribute("wanAndRcpDetail", wanAndRcpDetailDTO); // 완제품 정보
		model.addAttribute("wonList", wonList); // 원재료 리스트
		return "productWan/recipeModifyInForm";
	}
	
	// 레시피 수정
	@PostMapping("/recipeModify")
	public String recipeModify(@RequestParam("materialsJson") String newRecipe , RedirectAttributes redirectAttributes) {
		log.info("newRecipe 수정->"+newRecipe);
		int product_code = 0;
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<RecipeDTO> recipeList = mapper.readValue(newRecipe, new TypeReference<List<RecipeDTO>>(){});
			
			if( !recipeList.isEmpty() ) {
				product_code = recipeList.get(0).getProduct_wan_code();
				
				// 1. recipeList 파싱 후 바로 원재료 총합 계산 & 생산단위 추출
				int totalRecipe = 0;
				for(RecipeDTO recipeDTO : recipeList) {
					totalRecipe += recipeDTO.getRecipe_amount(); // 원재료 무게 합산
				}
				
				// 3. 제품 테이블에서 생산단위 조회
				int product_pack = productService.findPack(product_code);
				
				// 4. 기본 중량 계산
				double weight = (double)totalRecipe/product_pack;
				
				// 5. 기본 중량 저장
				productService.saveWeight(product_code , weight);
			}
				productService.recipeModify(recipeList);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JsonMappingException->"+e.getMessage());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("JsonProcessingException->"+e.getMessage());
		}
		
		redirectAttributes.addAttribute("product_code" , product_code);
		return "redirect:/km/wanAndRcpDetailInForm";
	}
	
	// 완제품 판매 여부 결정 및 가격 변동일 수정
	@PostMapping("/wanProductDel")
	public String wanProductDel(ProductWanDTO productWanDTO , HttpServletRequest request , RedirectAttributes redirectAttributes , @AuthenticationPrincipal AccountDto emp ) {
		log.info("wanProductDel productWanDTO->"+productWanDTO.getProduct_code()+" , "+productWanDTO.isProduct_isdel());
		
		/* 로그인한 사원이 판매여부 버튼 누른 뒤 가격 table에 등록자로 넣어주기 위해
		 * HttpSession session = request.getSession(); EmpDto loginEmp = (EmpDto)
		 * session.getAttribute("loginEmp");
		 */
		
		productWanDTO.setProduct_reg_code(emp.getEmp_code());
		productService.wanProductDel(productWanDTO);
		
		redirectAttributes.addAttribute("product_code" , productWanDTO.getProduct_code());
		return "redirect:/km/wanAndRcpDetailInForm";
	}
	
	// 가격 조정 폼
	@GetMapping("/wanPriceModifyInForm")
	public String wanPriceModifyInForm(ProductWanDTO productWanDTO , Model model) {
		log.info("productWanDTO->"+productWanDTO);
		
		List<ProductPriceDTO> priceHistory = productService.priceHistory(productWanDTO.getProduct_code());
		log.info("priceHistory->"+priceHistory);
		
		double tot_wan_price = productService.launchPrice(productWanDTO.getProduct_code());
		log.info("tot_wan_price->" + tot_wan_price);
		model.addAttribute("tot_wan_price" , tot_wan_price);
		model.addAttribute("priceHistory" , priceHistory);
		return "productWan/wanPriceModifyInForm";
	}
	
	// 가격 조정
	@PostMapping("/wanPriceModify")
	public String wanPriceModify(ProductPriceDTO priceDTO , RedirectAttributes redirectAttributes , @AuthenticationPrincipal AccountDto emp , Model model) {
		System.out.println("priceDTO->"+priceDTO);
		
		// 로그인한 사원 정보
		priceDTO.setPrice_reg_code(emp.getEmp_code());
		productService.wanPriceModify(priceDTO);
		
		List<ProductPriceDTO> priceHistory = productService.priceHistory(priceDTO.getProduct_code());
		double tot_wan_price = productService.launchPrice(priceDTO.getProduct_code());
		
		model.addAttribute("tot_wan_price" , tot_wan_price);
		model.addAttribute("priceHistory" , priceHistory);
	//  RedirectAttributes는 단순 값(String, int 등)만 전송 가능하고, 컬렉션이나 객체는 안 돼
	//  redirectAttributes.addAttribute("priceHistory" , priceHistory);
	//	redirectAttributes.addAttribute("product_code" , priceDTO.getProduct_code());
		return "productWan/wanPriceModifyInForm";
	}
	
	
	
	
	

}
