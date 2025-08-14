package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.StockDao;
import com.oracle.coffee.dto.MagamStatusDto;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.SilsaDto;
import com.oracle.coffee.dto.StockDto;

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
	public boolean isClosedMagam() {
		MagamStatusDto magam = stockDao.getTodayMagam();
		if (magam == null || magam.getMagam_status() == 0) return false;
		return true;
	}

	@Override
	public void closeTodayMagam() throws Exception {
		// 이미 마감된 경우 예외처리
		if (isClosedMagam()) throw new Exception();
		
		stockDao.closeTodayMagam();
	}

	@Override
	public void cancelTodayMagam() throws Exception {
		// 오늘자 마감 안된 경우 예외처리
		MagamStatusDto magam = stockDao.getTodayMagam();
		if (magam == null || magam.getMagam_status() != 1) throw new Exception();
		
		stockDao.cancelTodayMagam();
	}

	@Override
	public int getRealStock(int productCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<StockDto> getAllStock() {
		List<StockDto> stocks = stockDao.getAllStock();
		return stocks;
	}

	@Override
	public void saveSilsa(List<SilsaDto> silsaList, int empCode) {
		for (SilsaDto data : silsaList) {
			data.setSilsa_reg_code(empCode);
		}
		stockDao.saveSilsa(silsaList);
	}

}
