package com.oracle.coffee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.coffee.dao.SWProductPriceDao;
import com.oracle.coffee.dto.WonProductPriceDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SWProductPriceServiceImpl implements SWProductPriceService {
	
	private final PlatformTransactionManager transactionManager;
	private final SWProductPriceDao	swProductPriceDao;

	@Override
	public int wonProductPriceSave(WonProductPriceDto wonProductPriceDto) {
		System.out.println("SWProductPriceServiceImpl wonProductPriceSave start...");
		
		int isPriceCheck = swProductPriceDao.isPriceCheck(wonProductPriceDto);
		System.out.println("SWProductPriceServiceImpl wonProductPriceSave isPriceCheck : " + isPriceCheck);
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int result = 0;
		try {
			if(isPriceCheck > 0) {
				swProductPriceDao.updateEndDate(wonProductPriceDto);
			}
			wonProductPriceDto.setEnd_date("99/12/31");
			result = swProductPriceDao.wonProductPriceSave(wonProductPriceDto);
			
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductPriceServiceImpl wonProductPriceSave Exception : " + e.getMessage());
			throw new RuntimeException("가격 등록 중 오류 발생: " + e.getMessage());
		}
		return result;
	}
	
}
