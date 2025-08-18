package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.coffee.dto.PurchaseDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SWPurchaseDaoImpl implements SWPurchaseDao {

	private final PlatformTransactionManager transactionManager;
	private final SqlSession 	session;

	@Override
	public int purchaseSave(List<PurchaseDto> purchaseDtoList) {
		System.out.println("SWPurchaseDaoImpl purchaseSave start...");
		
		System.out.println("SWPurchaseDaoImpl purchaseSave purchaseDtoList : " + purchaseDtoList);
		int purchase_result = 0;
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		boolean first = true;
		try {
			for(PurchaseDto dto : purchaseDtoList) {
				if(first) {
					purchase_result = session.insert("purchaseSave", dto);
					first = false;
				}
				session.insert("purchaseDetailSave", dto);
			}
			transactionManager.commit(txStatus);
			purchase_result = 1;
		} catch (Exception e) {
		    transactionManager.rollback(txStatus);
			System.out.println("SWPurchaseDaoImpl purchaseSave Exception : " + e.getMessage());
			purchase_result = 0;
		}
		
		return purchase_result;
	}

	@Override
	public int totalPurchaseCnt(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseDaoImpl totalPurchaseCnt start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int totalPurchaseCnt = 0;
		
		try {
			totalPurchaseCnt = session.selectOne("totalPurchaseCnt", purchaseDto);
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWPurchaseDaoImpl totalPurchaseCnt Exception : " + e.getMessage());
		}
		return totalPurchaseCnt;
	}

	@Override
	public List<PurchaseDto> purchaseList(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseDaoImpl purchaseList start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<PurchaseDto> purchaseList = null;
		try {
			purchaseList = session.selectList("purchaseList", purchaseDto);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWPurchaseDaoImpl purchaseList Exception : " + e.getMessage());
		}
		return purchaseList;
	}

	@Override
	public PurchaseDto purchaseDetail(int purchase_code) {
		System.out.println("SWPurchaseDaoImpl purchaseDetail start...");
		System.out.println(purchase_code);
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		PurchaseDto purchaseDetail = null;
		try {
			purchaseDetail = session.selectOne("purchaseDetail", purchase_code);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWPurchaseDaoImpl purchaseDetail Exception : " + e.getMessage());
		}
		return purchaseDetail;
	}

	@Override
	public void purchaseApprove(int purchase_code) {
		System.out.println("SWPurchaseDaoImpl purchaseApprove start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int magamStatus = session.selectOne("magamCheck");
		System.out.println("SWPurchaseDaoImpl purchaseApprove magamCheck : " + magamStatus);
		
		try {
			if(magamStatus == 0) {
				session.update("purchaseApprove", purchase_code);
				transactionManager.commit(txStatus);
			}
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			e.printStackTrace();
			System.out.println("SWPurchaseDaoImpl purchaseApprove Exception : " + e.getMessage());
		}
	}

	@Override
	public void purchaseRefuse(PurchaseDto purchaseRefuse) {
		System.out.println("SWPurchaseDaoImpl purchaseRefuse start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			session.update("purchaseRefuse", purchaseRefuse);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			e.printStackTrace();
			System.out.println("SWPurchaseDaoImpl purchaseRefuse Exception : " + e.getMessage());
		}
	}

	@Override
	public List<PurchaseDto> purchaseDetailList(int purchase_code) {
		System.out.println("SWPurchaseDaoImpl purchaseDetailList start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<PurchaseDto> purchaseDetailList = null;
		try {
			purchaseDetailList = session.selectList("purchaseDetailList", purchase_code);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWPurchaseDaoImpl purchaseDetailList Exception : " + e.getMessage());
		}
		return purchaseDetailList;
	}

}
