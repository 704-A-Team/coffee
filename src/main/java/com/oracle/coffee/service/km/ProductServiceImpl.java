package com.oracle.coffee.service.km;

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
	@Override
	public int wanRegister(ProductWanDTO productDTO, ProductPriceDTO priceDTO) {
		System.out.println("wanRegister Start");
		int result = 0;
		
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

}
