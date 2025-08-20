package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.WonProductPriceDto;

public interface SWProductPriceService {

	int 						wonProductPriceSave(WonProductPriceDto wonProductPriceDto);
	List<WonProductPriceDto> 	wonProductPriceList(WonProductPriceDto wonProductPriceDto);
	int 						isPriceCheck(int product_code);

}
