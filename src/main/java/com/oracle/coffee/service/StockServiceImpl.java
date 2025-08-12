package com.oracle.coffee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.StockDao;
import com.oracle.coffee.dto.MagamStatusDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{
	
	private final StockDao stockDao;

	@Override
	public boolean isClosedMagam() {
		MagamStatusDto magam = stockDao.getTodayMagam();
		if (magam == null) return false;
		return true;
	}

	@Override
	public void closeTodayMagam() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelTodayMagam() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRealStock(int productCode) {
		// TODO Auto-generated method stub
		return 0;
	}

}
