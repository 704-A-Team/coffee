package com.oracle.coffee.service;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.SWProductPriceDao;
import com.oracle.coffee.dto.WonProductPriceDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SWProductPriceServiceImpl implements SWProductPriceService {
	private final SWProductPriceDao	swProductPriceDao;

	@Override
	public int wonProductPriceSave(WonProductPriceDto wonProductPriceDto) {
		System.out.println("SWProductPriceServiceImpl wonProductPriceSave start...");
		
		int result = swProductPriceDao.wonProductPriceSave(wonProductPriceDto);
		
		return result;
	}
	
}
