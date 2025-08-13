package com.oracle.coffee.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import com.oracle.coffee.dto.WonProductPriceDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SWProductPriceDaoImpl implements SWProductPriceDao {
	
	private final PlatformTransactionManager transactionManager;
	private final SqlSession session;
	
	@Override
	public int wonProductPriceSave(WonProductPriceDto wonProductPriceDto) {
		System.out.println("SWProductPriceDaoImpl wonProductPriceSave start...");
		System.out.println("SWProductPriceDaoImpl wonProductPriceSave[insert] wonProductPriceDto-->"+wonProductPriceDto);
		
		return session.insert("wonProductPriceSave", wonProductPriceDto);
	}

	@Override
	public int isPriceCheck(WonProductPriceDto wonProductPriceDto) {
		System.out.println("SWProductPriceDaoImpl isPriceCheck start...");
		
		return session.selectOne("isPriceCheck", wonProductPriceDto);
	}

	@Override
	public void updateEndDate(WonProductPriceDto wonProductPriceDto) {
		System.out.println("SWProductPriceDaoImpl updateEndDate start...");
		System.out.println("SWProductPriceDaoImpl updateEndDate wonProductPriceDto-->"+wonProductPriceDto);
		
		session.update("updateEndDate", wonProductPriceDto);
	}
	
	
}
