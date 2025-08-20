package com.oracle.coffee.dao;

import java.util.List;

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
	public int isPriceCheck(int product_code) {
		System.out.println("SWProductPriceDaoImpl isPriceCheck start...");
		
		return session.selectOne("isPriceCheck", product_code);
	}

	@Override
	public void updateEndDate(WonProductPriceDto wonProductPriceDto) {
		System.out.println("SWProductPriceDaoImpl updateEndDate start...");
		System.out.println("SWProductPriceDaoImpl updateEndDate wonProductPriceDto-->"+wonProductPriceDto);
		
		session.update("updateEndDate", wonProductPriceDto);
	}

	@Override
	public List<WonProductPriceDto> wonProductPriceList(WonProductPriceDto wonProductPriceDto) {
		System.out.println("SWProductPriceDaoImpl wonProductPriceList start...");
		
		return session.selectList("wonProductPriceList", wonProductPriceDto);
	}
	
	
}
