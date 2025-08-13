package com.oracle.coffee.dao;

import com.oracle.coffee.dto.WonProductPriceDto;

public interface SWProductPriceDao {

	int 				wonProductPriceSave(WonProductPriceDto wonProductPriceDto);
	int 				isPriceCheck(WonProductPriceDto wonProductPriceDto);
	void 				updateEndDate(WonProductPriceDto wonProductPriceDto);

}
