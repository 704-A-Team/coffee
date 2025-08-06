package com.oracle.coffee.dao.km;

import java.util.List;

import com.oracle.coffee.dto.km.ProductWanDTO;
import com.oracle.coffee.dto.km.ProductImgDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;
import com.oracle.coffee.dto.km.RecipeDTO;
import com.oracle.coffee.dto.km.WanAndRecipeDTO;

public interface ProductDao {

	void 					wanRegister(ProductWanDTO productDTO);
	
	void 					wanImgRegister(List<ProductImgDTO> productImgDTO);

	int 					priceRegister(ProductPriceDTO priceDTO);

	int 					countTotal();

	List<ProductWanDTO> 	wonList();

	void 					wanRecipeSave(RecipeDTO recipe);

	List<ProductWanDTO> 	wanList(ProductWanDTO productDTO);

	WanAndRecipeDTO 		wanAndRcpDetailInForm(int product_code);

	WanAndRecipeDTO 		wanModifyInForm(int product_code);

	ProductWanDTO 			getProductImg(int product_code);

	ProductWanDTO 			wanModify(ProductWanDTO productDTO);

	ProductWanDTO 			delImgModify(ProductWanDTO productDTO);

	void 					recipeDel(int pdwan_code);

	void 					productDelUpdate(ProductWanDTO productWanDTO);

	void 					priceBeforeEnd(ProductPriceDTO productPriceDTO);

	ProductPriceDTO 		prePrice(ProductPriceDTO priceDTO);

	void 					priceAfterStart(ProductPriceDTO priceDTO);


}