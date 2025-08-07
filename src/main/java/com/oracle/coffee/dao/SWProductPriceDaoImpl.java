package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.coffee.dto.ProvideDto;
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
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int result = 0;
		try {
			result = session.insert("wonProductPriceSave", wonProductPriceDto);
			transactionManager.commit(txStatus);
			result = 1;
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductPriceDaoImpl wonProductPriceSave Exception : " + e.getMessage());
		}
		return result;
	}
	
	
}
