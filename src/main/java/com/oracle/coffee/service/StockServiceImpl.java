package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.StockDao;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.stock.MagamPageDto;
import com.oracle.coffee.dto.stock.MagamStatusDto;
import com.oracle.coffee.dto.stock.MonthMagamDto;
import com.oracle.coffee.dto.stock.SilsaDto;
import com.oracle.coffee.dto.stock.StockDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{
	
	private final StockDao stockDao;
	
	@Override
	public PageRespDto<StockDto, Paging> getStockList(PageRequestDto page) {
		int totalCount = stockDao.totalStockCount();
		Paging paging = new Paging(totalCount, String.valueOf(page.getPage()));
		page.setStart(paging.getStart());
		page.setEnd(paging.getEnd());
		
		List<StockDto> list = stockDao.getStockList(page);
		return new PageRespDto<StockDto, Paging>(list, paging);
	}
	
	@Override
	public boolean isClosedMonth() {
		MagamStatusDto monthMagam = stockDao.getMonthMagam();
		if (monthMagam != null) return true;
		return false;
	}
	
	@Override
	public boolean isClosedToday() {
		MagamStatusDto magam = stockDao.getTodayMagam();
		if (magam == null || magam.getMagam_status() == 0) return false;
		return true;
	}

	@Override
	public boolean isClosedMagam() {
		if (isClosedMonth()) return true;
		if (isClosedToday()) return true;
		return false;
	}

	@Override
	public void closeTodayMagam() throws Exception {
		// 일마감 후 월마감 진행됨
		if (isClosedMonth()) throw new Exception();
		
		// 일마감된 경우 예외처리
		if (isClosedToday()) throw new Exception();
		
		stockDao.closeTodayMagam();
	}

	@Override
	public void cancelTodayMagam() throws Exception {
		// 월마감 된 경우 마감 취소 불가능
		if (isClosedMonth()) throw new Exception();
		
		// 오늘자 마감 안된 경우 예외처리
		if (!isClosedToday()) throw new Exception();
		
		stockDao.cancelTodayMagam();
	}

	@Override
	public int magamCheck() {
		System.out.println("StockServiceImpl magamCheck start...");
		int result = 0;
		if(isClosedMonth()) {
			result = 2;
		} else {
			result = stockDao.magamCheck();
		}
		
		return result;
    }

    public List<StockDto> getAllStock() {
		List<StockDto> stocks = stockDao.getAllStock();
		return stocks;
	}

	@Override
	public void saveSilsa(List<SilsaDto> silsaList, int empCode) throws Exception {
		// 오늘 날짜 기준 upsert
		stockDao.deleteTodaySilsa();
		
		for (SilsaDto data : silsaList) {
			data.setSilsa_reg_code(empCode);
		}
		stockDao.saveSilsa(silsaList);
	}

	@Override
	public PageRespDto<MonthMagamDto, Paging> getMonthMagams(PageRequestDto page) {
		int totalCount = stockDao.totalMonthMagam();
		
		Paging paging = new Paging(totalCount, String.valueOf(page.getPage()));
		
		page.setStart(paging.getStart());
		page.setEnd(paging.getEnd());
		List<MonthMagamDto> list = stockDao.getMonthMagamList(page);
		return new PageRespDto<MonthMagamDto, Paging>(list, paging);
	}

	@Override
	public PageRespDto<MonthMagamDto, Paging> getMonthMagamProducts(PageRequestDto page, MagamPageDto magamPage) {
		int totalCount = stockDao.totalMonthMagamPrds(magamPage);
		
		Paging paging = new Paging(totalCount, String.valueOf(page.getPage()));
		
		magamPage.setStart(paging.getStart());
		magamPage.setEnd(paging.getEnd());
		List<MonthMagamDto> list = stockDao.getMonthMagamPrds(magamPage);
		return new PageRespDto<MonthMagamDto, Paging>(list, paging);
	}

	@Override
	public void closeMonthMagam() throws Exception {
		if (!isClosedToday()) closeTodayMagam();
		
		stockDao.closeMonthMagam();
	}

	@Override
	public List<SilsaDto> getMonthSilsa() {
		return stockDao.getMonthSilsa();
	}

}
