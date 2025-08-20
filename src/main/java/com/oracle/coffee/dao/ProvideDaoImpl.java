package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.coffee.dto.ProvideDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProvideDaoImpl implements ProvideDao {
	private final SqlSession session;
	private final PlatformTransactionManager transactionManager;

	@Override
	public int provideSave(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl provideSave start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int provide_code = 0;
		try {
			provide_code = session.insert("provideSave", provideDto);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl provideSave Exception : " + e.getMessage());
		}
		return provide_code;
	}

	@Override
	public int totalProvide(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl totalProvide start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int totalProvideCount = 0;
		try {
			totalProvideCount = session.selectOne("totalProvide", provideDto);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl totalProvide Exception : " + e.getMessage());
		}
		return totalProvideCount;
	}

	@Override
	public List<ProvideDto> provideList(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl provideList start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<ProvideDto> provideList = null;
		try {
			 provideList = session.selectList("provideList", provideDto);
			 transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl provideList Exception : " + e.getMessage());
		}
		return provideList;
	}

	@Override
	public ProvideDto provideDetail(int provide_code) {
		System.out.println("ProvideDaoImpl provideDetail start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		ProvideDto provideDetail = null;
		try {
			provideDetail = session.selectOne("provideDetail", provide_code);
			System.out.println("ProvideDaoImpl provideDetail provide_code : " + provide_code);
			System.out.println("ProvideDaoImpl provideDetail provideDetail : " + provideDetail);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl provideDetail Exception : " + e.getMessage());
		}
		return provideDetail;
	}

	@Override
	public int provideModify(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl provideModify start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int provideModify = 0;
		try {
			session.update("provideModify", provideDto);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl provideModify Exception : " + e.getMessage());
		}
		return provideModify;
	}

	@Override
	public void provideDelete(ProvideDto provideDetail) {
		System.out.println("ProvideDaoImpl provideDelete start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			session.update("provideDelete", provideDetail);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl provideDelete Exception : " + e.getMessage());
		}
	}

	@Override
	public ProvideDto getProvideInfo(ProvideDto provideDto) {
		System.out.println("ProvideDaoImpl getProvideInfo start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		ProvideDto getProvideInfo = null;
		try {
			getProvideInfo = session.selectOne("getProvideInfo", provideDto);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl getProvideInfo Exception : " + e.getMessage());
		}
		return getProvideInfo;
	}

	@Override
	public List<ProvideDto> getProvideByProduct(int product_code) {
		System.out.println("ProvideDaoImpl getProvideByProduct start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<ProvideDto> getProvideByProduct = null;
		try {
			getProvideByProduct = session.selectList("getProvideByProduct", product_code);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProvideDaoImpl getProvideByProduct Exception : " + e.getMessage());
		}
		return getProvideByProduct;
	}

	@Override
	public List<ProvideDto> getProductsByClient(int client_code) {
		System.out.println("ProvideDaoImpl getProductsByClient start...");
		
		return session.selectList("getProductsByClient", client_code);
	}

	
	
}
