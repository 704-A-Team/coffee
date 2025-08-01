package com.oracle.coffee.service.km;

import java.util.List;

import com.oracle.coffee.dto.km.ProductWanDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;
import com.oracle.coffee.dto.km.RecipeDTO;
import com.oracle.coffee.dto.km.WanAndRecipeDTO;

public interface ProductService {

	int 					wanRegister(ProductWanDTO productDTO, ProductPriceDTO priceDTO);

	int 					countTotal();

	List<ProductWanDTO> 		wonList();

	void 					wanRecipeSave(RecipeDTO recipe);

	List<ProductWanDTO> 		wanList(ProductWanDTO productDTO);
	// 완제품 코드(IN) --> 완제품과 레시피Dto(OUT)
	WanAndRecipeDTO 		wanAndRcpDetailInForm(int product_code);

	WanAndRecipeDTO 		wanModifyInForm(int product_code);

}
