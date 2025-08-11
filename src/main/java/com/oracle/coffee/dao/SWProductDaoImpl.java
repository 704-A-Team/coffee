package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.km.ProductImgDTO;

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
		
		return session.selectOne("totalProduct");
	}

	@Override
	public List<ProductDto> productList(ProductDto productDto) {
		System.out.println("ProductDaoImpl productList start...");
		
		return session.selectList("wonProductList", productDto);
	}

	@Override
	public int totalWonProduct(ProductDto productDto) {
		System.out.println("ProductDaoImpl totalWonProduct start...");
		
		return session.selectOne("totalWonProduct", productDto);
	}

	@Override
	public ProductDto wonProductDetail(int product_code) {
		System.out.println("ProductDaoImpl productDetail start...");
		
		ProductDto wonProductDetail = session.selectOne("wonProductDetail", product_code);
		
		List<ProductImgDTO> imgList = session.selectList("wonProductImgList", product_code);
        wonProductDetail.setWonImgList(imgList);
	        
		return wonProductDetail;
	}

	@Override
	public int wonProductModify(ProductDto productDto) {
		System.out.println("ProductDaoImpl wonProductModify start...");
		
		System.out.println("ProductDaoImpl wonProductModify productDto : " + productDto);
		int wonProduct_code = session.update("wonProductModify", productDto);
		
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
		
		return session.selectList("productInfo", product_type);
	}

	@Override
	public List<ProductDto> productIsList(int product_type) {
		System.out.println("ProductDaoImpl productIsList start...");
		
		return session.selectList("productIsList", product_type);
	}

	@Override
	public void wonProductImgSave(List<ProductImgDTO> productImgList) {
		System.out.println("ProductDaoImpl wonProductImgSave start...");
		TransactionStatus txStatus = 
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			session.insert("wonProductImgSave", productImgList);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("ProductDaoImpl wonProductImgSave Exception : " + e.getMessage());
		}
	}

	@Override
	public void deleteProductImgs(int product_code) {
	    System.out.println("ProductDaoImpl deleteProductImgs start...");
	    
        session.delete("deleteProductImgs", product_code);
	}

	@Override
	public void insertProductImgs(List<ProductImgDTO> imgList) {
		System.out.println("ProductDaoImpl insertProductImgs start...");
	    
	    if (imgList == null || imgList.isEmpty()) return;

	    for (ProductImgDTO imgDto : imgList) {
	        session.insert("insertProductImg", imgDto);
	    }
	}

	@Override
	public List<ProductDto> wonProductAllList() {
		System.out.println("ProductDaoImpl wonProductAllList start...");
	    
		return session.selectList("wonProductAllList");
	}



	
}
