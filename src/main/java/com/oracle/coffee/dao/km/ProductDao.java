package com.oracle.coffee.dao.km;

import java.util.List;

import com.oracle.coffee.dto.km.ProductDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;

public interface ProductDao {

	void 				wanRegister(ProductDTO productDTO);

	int 				priceRegister(ProductPriceDTO priceDTO);

	int 				countTotal();

	List<ProductDTO> 	wonList();

}
