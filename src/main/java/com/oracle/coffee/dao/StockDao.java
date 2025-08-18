package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.MagamStatusDto;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.StockDto;

public interface StockDao {
	public MagamStatusDto 		getTodayMagam();
	public int 					totalCount();
	public List<StockDto> 		list(PageRequestDto page);
	public void 				closeTodayMagam();
	public void 				cancelTodayMagam();
	public int 					magamCheck();
}
