package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
	
	private final SqlSession session;
	
	@Override
	public int wonProductSave(ProductDto productDto) {
		System.out.println("ProductDaoImpl wonProductSave start...");
		
		int wonProduct_code = session.insert("wonProductSave", productDto);
		
		return wonProduct_code;
	}

	@Override
	public int totalProduct() {
		System.out.println("ProductDaoImpl totalProduct start...");
		
		int totalProduct = session.selectOne("totalProduct");
		
		return totalProduct;
	}

	@Override
	public List<ProductDto> productList(ProductDto productDto) {
		System.out.println("ProductDaoImpl productList start...");
		
		List<ProductDto> productDtoList = session.selectList("wonProductList", productDto);
		
		return productDtoList;
	}

	@Override
	public int totalWonProduct() {
		System.out.println("ProductDaoImpl totalWonProduct start...");
		
		int totalWonProduct = session.selectOne("totalWonProduct");
		
		return totalWonProduct;
	}

	@Override
	public ProductDto wonProductDetail(int product_code) {
		System.out.println("ProductDaoImpl productDetail start...");
		
		ProductDto wonProductDetail = session.selectOne("wonProductDetail", product_code);
		
		return wonProductDetail;
	}

	@Override
	public int wonProductModify(ProductDto productDto) {
		System.out.println("ProductDaoImpl wonProductSave start...");
		
		int wonProduct_code = session.update("wonProductModify", productDto);
		
		return wonProduct_code;
	}

	@Override
	public void wonProductDelete(ProductDto productDto) {
		System.out.println("ProductDaoImpl wonProductDelete start...");
		
		session.update("wonProductDelete", productDto);
	}

	@Override
	public List<ProductDto> getProductInfo(int product_type) {
		System.out.println("ProductDaoImpl wonProductDelete start...");
		
		List<ProductDto> productList = session.selectList("productInfo", product_type);
		
		return productList;
	}
	
}
