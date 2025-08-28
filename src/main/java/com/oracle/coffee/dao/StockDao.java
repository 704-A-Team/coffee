package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.stock.MagamPageDto;
import com.oracle.coffee.dto.stock.MagamStatusDto;
import com.oracle.coffee.dto.stock.MonthMagamDto;
import com.oracle.coffee.dto.stock.SilsaDto;
import com.oracle.coffee.dto.stock.StockDto;

public interface StockDao {
	public MagamStatusDto         getTodayMagam();
	public int                    totalStockCount(PageRequestDto page);
	public List<StockDto>         getStockList(PageRequestDto page);
	public void                   closeTodayMagam();
	public void                   cancelTodayMagam();
	public List<StockDto>         getAllStock();
	public void                   saveSilsa(List<SilsaDto> silsaList);
	public MagamStatusDto         getMonthMagam();
	public int                    totalMonthMagam();
	public List<MonthMagamDto>    getMonthMagamList(PageRequestDto page);
	public int                    totalMonthMagamPrds(MagamPageDto magamPage);
	public List<MonthMagamDto>    getMonthMagamPrds(MagamPageDto magamPage);
	public int 					  magamCheck();
	public void 				  closeMonthMagam();
	public List<SilsaDto> getTodaySilsa();
	public void deleteTodaySilsa();
	public int totalSilsa(PageRequestDto page);
	public List<SilsaDto> getSilsaList(PageRequestDto page);
}
