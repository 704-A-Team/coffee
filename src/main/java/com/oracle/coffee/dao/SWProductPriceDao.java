package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.WonProductPriceDto;

public interface SWProductPriceDao {

	int 						wonProductPriceSave(WonProductPriceDto wonProductPriceDto);
	int 						isPriceCheck(int product_code);
	void 						updateEndDate(WonProductPriceDto wonProductPriceDto);
	List<WonProductPriceDto> 	wonProductPriceList(WonProductPriceDto wonProductPriceDto);

}
