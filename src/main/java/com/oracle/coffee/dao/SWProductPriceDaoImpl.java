package com.oracle.coffee.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SWProductPriceDaoImpl implements SWProductPriceDao {
	private final PlatformTransactionManager transactionManager;
	private final SqlSession session;
	
	
}
