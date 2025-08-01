package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.SWProductDao;
import com.oracle.coffee.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class SWProductServiceImpl implements SWProductService {
	
	private final SWProductDao productDao;
	
	@Override
	public int wonProductSave(ProductDto productDto) {
		System.out.println("ProductServiceImpl wonProductSave start...");
		
		int wonProduct_code = productDao.wonProductSave(productDto);
	
		return wonProduct_code;
	}

	@Override
	public int totalProduct() {
		System.out.println("ProductServiceImpl totalProduct start...");
		
		int totalProduct = productDao.totalProduct();
		
		return totalProduct;
	}

	@Override
	public List<ProductDto> productList(ProductDto productDto) {
		System.out.println("ProductServiceImpl productList start...");
		
		List<ProductDto> productDtoList = productDao.productList(productDto);
		System.out.println("ProductServiceImpl productList productDtoList.size() : " + productDtoList.size());
		
		return productDtoList;
	}

	@Override
	public int totalWonProduct() {
		System.out.println("ProductServiceImpl totalWonProduct start...");
		
		int totalWonProduct = productDao.totalWonProduct();
		
		return totalWonProduct;
	}

	@Override
	public ProductDto wonProductDetail(int product_code) {
		System.out.println("ProductServiceImpl wonProductDetail start...");
		
		ProductDto wonProductDetail = productDao.wonProductDetail(product_code);
		
		return wonProductDetail;
	}

	@Override
	public int wonProductModify(ProductDto productDto) {
		System.out.println("ProductServiceImpl wonProductModify start...");
		
		int wonProduct_code = productDao.wonProductModify(productDto);
	
		return wonProduct_code;
	}

	@Override
	public void wonProductDelete(int product_code) {
		System.out.println("ProductServiceImpl wonProductDelete start...");
		
		ProductDto wonProductDetail = productDao.wonProductDetail(product_code);
		wonProductDetail.setProduct_isdel(1);
		
		productDao.wonProductDelete(wonProductDetail);
	}

	@Override
	public List<ProductDto> productIsList(int product_type) {
		log.info("ProductServiceImpl productAllList start...");
		
		return productDao.productIsList(product_type);
	}

}
