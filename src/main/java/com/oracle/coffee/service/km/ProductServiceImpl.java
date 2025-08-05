package com.oracle.coffee.service.km;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.km.ProductDao;
import com.oracle.coffee.dto.km.ProductWanDTO;
import com.oracle.coffee.dto.km.ProductImgDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;
import com.oracle.coffee.dto.km.RecipeDTO;
import com.oracle.coffee.dto.km.WanAndRecipeDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
//@Transactionals
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao productDao;
	
	// 완제품 등록(완제품, 가격 동시 등록)
	@Transactional
	@Override
	public int wanRegister(ProductWanDTO productDTO, ProductPriceDTO priceDTO) {
		System.out.println("wanRegister Start");
		int result = 0;
		try {
		// 1. 완제품 등록  --> 제품 코드 받기
		productDao.wanRegister(productDTO);
		// 2. 완제품 이미지 등록
		List<ProductImgDTO> productImgList = new ArrayList<>();
	//	List<String> file = productDTO.getUploadFileNames();
		for(int i = 0 ; i < productDTO.getUploadFileNames().size(); i++) {
			String file_name = productDTO.getUploadFileNames().get(i);
			
			ProductImgDTO pImgDTO = new ProductImgDTO();
			pImgDTO.setFile_name(file_name);
			pImgDTO.setOrd(i); // 0부터 시작
			pImgDTO.setProduct_code(productDTO.getProduct_code());
			log.info("pImgDTO->"+pImgDTO);
			productImgList.add(pImgDTO);
		}
		System.out.println("ProductServiceImpl wanRegister productImgList->"+productImgList);
		productDao.wanImgRegister(productImgList);
		// 3. 가격 등록 --> 동일 제품 코드로 입력
		priceDTO.setProduct_code(productDTO.getProduct_code());
		System.out.println("ProductServiceImpl wanRegister priceDTO->"+priceDTO);
		result = productDao.priceRegister(priceDTO);
		} catch (Exception e) {
			  throw new RuntimeException("완제품 등록 실패", e);
		}
		return result;
	}
	
	// 페이징 완제품 총개수
	@Override
	public int countTotal() {
		int total = productDao.countTotal();
		return total;
	}
	
	// 레시피 등록 폼에서 필요한 > 원재료 드롭다운 박스 > 원재료 리스트
	@Override
	public List<ProductWanDTO> wonList() {
		List<ProductWanDTO> wonList = productDao.wonList();
		return wonList;
	}
	
	// 레시피 등록
	@Override
	public void wanRecipeSave(RecipeDTO recipe) {
		productDao.wanRecipeSave(recipe);
		
	}
	
	// 완제품 List 가져오기 ( 3개 Table Join )
	@Override
	public List<ProductWanDTO> wanList(ProductWanDTO productDTO) {
		List<ProductWanDTO> wanList = productDao.wanList(productDTO);
		return wanList;
	}
	
	// 완제품 코드(IN) --> 완제품 Dto(OUT)
	@Override
	public WanAndRecipeDTO wanAndRcpDetailInForm(int product_code) {
		WanAndRecipeDTO wanModifyDTO = productDao.wanAndRcpDetailInForm(product_code);
		return wanModifyDTO;
	}
	
	// 완제품 코드(IN) --> 수정할 완제품 Dto(OUT) 
	@Override
	public WanAndRecipeDTO wanModifyInForm(int product_code) {
		WanAndRecipeDTO wanModifyDTO = productDao.wanModifyInForm(product_code);
		return wanModifyDTO;
	}
	
	// 완제품 수정 시 --> 제품코드로 이미지 찾아오기
	@Override
	public ProductWanDTO getProductImg(int product_code) {
		ProductWanDTO getProductImg = productDao.getProductImg(product_code);
		
		return getProductImg;
	}
	
	// 완제품 수정
	@Transactional
	@Override
	public ProductWanDTO wanModify(ProductWanDTO productDTO) {
		// 1. 완제품 정보 수정
		ProductWanDTO wanModifyDTO = productDao.wanModify(productDTO);
		log.info("수정 완료 된 완제품 정보->"+wanModifyDTO);
		// 2. 완제품 이미지 삭제
		ProductWanDTO delImgModifyDTO = productDao.delImgModify(productDTO);
		// 3. 완제품 이미지 추가(수정 등록)
		List<ProductImgDTO> addImgList = new ArrayList<>();
		for(int i = 0 ; i < productDTO.getExistingUploadFileNames().size(); i++) {
			String file_name = productDTO.getUploadFileNames().get(i);
			
			ProductImgDTO pImgDTO = new ProductImgDTO();
			pImgDTO.setFile_name(file_name);
			pImgDTO.setOrd(i); // 0부터 시작
			pImgDTO.setProduct_code(productDTO.getProduct_code());
			log.info("pImgDTO->"+pImgDTO);
			addImgList.add(pImgDTO);
		}
		System.out.println("ProductServiceImpl wanModify addImgList->"+addImgList);
		productDao.wanImgRegister(addImgList);
		
		return productDTO;
	}
	
	// 레시피 수정
	@Transactional
	@Override
	public void recipeModify(List<RecipeDTO> recipeList) {
		log.info("recipeList->"+recipeList);
		
		if( recipeList.isEmpty() || recipeList == null ) return;
		
		int pdwan_code = recipeList.get(0).getProduct_wan_code();
		
		// 삭제는 1번
		productDao.recipeDel(pdwan_code);
		
		// 등록 N번
		for(RecipeDTO recipeDTO : recipeList) {
			productDao.wanRecipeSave(recipeDTO);
		}
		
		
	}

	// 판매 등록 여부 및 가격 변동일 수정
	@Override
	@Transactional // Tbl 2개에서 정보 변동되므로 걸어두기
	public void wanProductDel(ProductWanDTO productWanDTO) {
		ProductPriceDTO priceDTO = new ProductPriceDTO();
		
		LocalDateTime tdate = LocalDateTime.now();
		LocalDateTime ydate = LocalDateTime.now().minusDays(1);
		String start_date = tdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String end_date = ydate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		priceDTO.setProduct_code(productWanDTO.getProduct_code());
		priceDTO.setStart_date(start_date);
		priceDTO.setEnd_date(end_date);
		priceDTO.setPrice_reg_date(tdate);
		// 변경할것
		priceDTO.setPrice_reg_code(productWanDTO.getProduct_reg_code());
		
		// 1. 제품Tbl -- 판매 여부 수정
		productDao.productDelUpdate(productWanDTO);
		// 2. 판매 여부 판별
		if(productWanDTO.isProduct_isdel() == true) {
			// 2-1-1. 이전 가격변동일 update
			productDao.priceBeforeEnd(priceDTO);
		} else {
			productDao.priceBeforeEnd(priceDTO);
			ProductPriceDTO prePrice = productDao.prePrice(priceDTO);
			productDao.wanStFalse(priceDTO);
		}
		
	}

}
