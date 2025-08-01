package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.coffee.dto.ProductDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SWProductDaoImpl implements SWProductDao {
	
	private final PlatformTransactionManager transactionManager;
	private final SqlSession session;
	
	@Override
	public int wonProductSave(ProductDto productDto) {
		System.out.println("ProductDaoImpl wonProductSave start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int wonProduct_code = 0;
		
		try {
			wonProduct_code = session.insert("wonProductSave", productDto);
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl wonProductSave Exception : " + e.getMessage());
		}
		return wonProduct_code;
	}

	@Override
	public int totalProduct() {
		System.out.println("ProductDaoImpl totalProduct start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int totalProduct = 0;
		
		try {
			totalProduct = session.selectOne("totalProduct");
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl totalProduct Exception : " + e.getMessage());
		}
		return totalProduct;
	}

	@Override
	public List<ProductDto> productList(ProductDto productDto) {
		System.out.println("ProductDaoImpl productList start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<ProductDto> productDtoList = null;
		
		try {
			productDtoList = session.selectOne("totalProduct");
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl productList Exception : " + e.getMessage());
		}
		return productDtoList;
	}

	@Override
	public int totalWonProduct() {
		System.out.println("ProductDaoImpl totalWonProduct start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int totalWonProduct = 0;
		try {
			totalWonProduct = session.selectOne("totalWonProduct");
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl totalWonProduct Exception : " + e.getMessage());
		}
		return totalWonProduct;
	}

	@Override
	public ProductDto wonProductDetail(int product_code) {
		System.out.println("ProductDaoImpl productDetail start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		ProductDto wonProductDetail = null;
		try {
			wonProductDetail = session.selectOne("wonProductDetail", product_code);
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl wonProductDetail Exception : " + e.getMessage());
		}
		return wonProductDetail;
	}

	@Override
	public int wonProductModify(ProductDto productDto) {
		System.out.println("ProductDaoImpl wonProductSave start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		int wonProduct_code = 0;
		try {
			wonProduct_code = session.update("wonProductModify", productDto);
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl wonProductModify Exception : " + e.getMessage());
		}
		return wonProduct_code;
	}

	@Override
	public void wonProductDelete(ProductDto productDto) {
		System.out.println("ProductDaoImpl wonProductDelete start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			session.update("wonProductDelete", productDto);
			transactionManager.commit(txStatus);

		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl wonProductDelete Exception : " + e.getMessage());
		}
	}

	@Override
	public List<ProductDto> getProductInfo(int product_type) {
		System.out.println("ProductDaoImpl wonProductDelete start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<ProductDto> productList = null;
		try {
			productList = session.selectList("productInfo", product_type);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl getProductInfo Exception : " + e.getMessage());
		}
		return productList;
	}

	@Override
	public List<ProductDto> productIsList(int product_type) {
		System.out.println("ProductDaoImpl productIsList start...");
		
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		List<ProductDto> productIsList = null;
		try {
			session.selectList("productIsList", product_type);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("SWProductDaoImpl productIsList Exception : " + e.getMessage());
		}
		return productIsList;
	}
	
}
