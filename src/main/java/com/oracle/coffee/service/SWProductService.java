package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.ProductDto;

public interface SWProductService {

	int 				wonProductSave(ProductDto productDto);
	int 				totalProduct();
	List<ProductDto> 	productList(ProductDto productDto); //paging
	int 				totalWonProduct(ProductDto productDto);
	ProductDto 			wonProductDetail(int product_code);
	int 				wonProductModify(ProductDto productDto);
	void 				wonProductDelete(int product_code);
	List<ProductDto> 	productIsList(int product_type);
	List<ProductDto> 	wonProductAllList();
	List<ProductDto> 	getNewProduct();

}
