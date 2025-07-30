package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.ProductDto;

public interface ProductDao {

	int 				wonProductSave(ProductDto productDto);
	int 				totalProduct();
	List<ProductDto> 	productList(ProductDto productDto);
	int 				totalWonProduct();
	ProductDto 			wonProductDetail(int product_code);
	int 				wonProductModify(ProductDto productDto);
	void 				wonProductDelete(ProductDto productDto);
	List<ProductDto> 	getProductInfo(int product_type);
	
}
