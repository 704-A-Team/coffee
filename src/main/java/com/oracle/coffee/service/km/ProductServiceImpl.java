package com.oracle.coffee.service.km;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.km.ProductDao;
import com.oracle.coffee.dto.km.ProductDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@Transactionals
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao productDao;
	
	// 완제품 등록(완제품, 가격 동시 등록)
	@Override
	public int wanRegister(ProductDTO productDTO, ProductPriceDTO priceDTO) {
		System.out.println("wanRegister Start");
		int result = 0;
		
		// 1. 완제품 등록  --> 제품 코드 받기
		productDao.wanRegister(productDTO);
		// 2. 가격 등록 --> 동일 제품 코드로 입력
		priceDTO.setProduct_code(productDTO.getProduct_code());
		result = productDao.priceRegister(priceDTO);
		return result;
	}
	// 페이징 완제품 총개수
	@Override
	public int countTotal() {
		int total = productDao.countTotal();
		return total;
	}
	
	// 레시피 등록 > 원재료 드롭다운 박스 > 원재료 리스트
	@Override
	public List<ProductDTO> wonList() {
		List<ProductDTO> wonList = productDao.wonList();
		return wonList;
	}

}
