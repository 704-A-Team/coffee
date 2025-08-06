package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.km.ProductImgDTO;

public interface SWProductDao {

	int 				wonProductSave(ProductDto productDto);
	int 				totalProduct();
	List<ProductDto> 	productList(ProductDto productDto);
	int 				totalWonProduct(ProductDto productDto);
	ProductDto 			wonProductDetail(int product_code);
	int 				wonProductModify(ProductDto productDto);
	void 				wonProductDelete(ProductDto productDto);
	List<ProductDto> 	getProductInfo(int product_type);
	List<ProductDto> 	productIsList(int product_type);
	void 				wonProductImgSave(List<ProductImgDTO> productImgList);
	void 				deleteProductImgs(int product_code);
	void 				insertProductImgs(List<ProductImgDTO> list);
	
}
