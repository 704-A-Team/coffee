package com.oracle.coffee.service.km;

import java.util.List;

import com.oracle.coffee.dto.km.ProductDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;

public interface ProductService {

	int 				wanRegister(ProductDTO productDTO, ProductPriceDTO priceDTO);

	int 				countTotal();

	List<ProductDTO> 	wonList();

}
