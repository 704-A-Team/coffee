package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.SWClientDto;
import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.ProvideDto;

public interface ProvideService {

	List<ProductDto> 		getProductInfo(int product_type);
	List<SWClientDto> 		getClientInfo(int client_type);
	int 					provideSave(ProvideDto provideDto);
	int 					totalProvide();
	List<ProvideDto> 		provideList(ProvideDto provideDto);
	ProvideDto 				provideDetail(int provide_code);
	int						provideModify(ProvideDto provideDto);
	void 					provideDelete(int provide_code);

}
