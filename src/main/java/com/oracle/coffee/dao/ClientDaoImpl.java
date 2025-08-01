package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.coffee.dto.SWClientDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClientDaoImpl implements ClientDao {
	
	private final SqlSession session;
	private final PlatformTransactionManager transactionManager;
	
	@Override
	public List<SWClientDto> getClientInfo(int client_type) {
		System.out.println("ClientDaoImpl getClientInfo start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<SWClientDto> clientList = null;
		try {
			clientList = session.selectList("clientInfo", client_type);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ClientDaoImpl getClientInfo Exception : " + e.getMessage());
		}
		return clientList;
	}

	@Override
	public List<SWClientDto> getClientsByProduct(int product_code) {
		System.out.println("ClientDaoImpl getClientsByProduct start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<SWClientDto> getClientsByProduct = null;
		try {
			getClientsByProduct = session.selectList("getClientsByProduct", product_code);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ClientDaoImpl getClientsByProduct Exception : " + e.getMessage());
		}
		return getClientsByProduct;
	}

}
