package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.MagamStatusDto;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.SilsaDto;
import com.oracle.coffee.dto.StockDto;

public interface StockDao {
	public MagamStatusDto getTodayMagam();
	public int totalStockCount();
	public List<StockDto> getStockList(PageRequestDto page);
	public void closeTodayMagam();
	public void cancelTodayMagam();
	public List<StockDto> getAllStock();
	public void saveSilsa(List<SilsaDto> silsaList);
}
