package com.oracle.oBootTodoApi01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootTodoApi01.dto.PageRequestDTO;
import com.oracle.oBootTodoApi01.dto.ProductDTO;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
	
	private final SqlSession session;

	@Override
	public int totalProduct() {
		int totalProduct = 0;
		try {
			totalProduct = session.selectOne("productTotal");
		} catch (Exception e) {
			System.out.println("totalProduct() Exception-> "+e.getMessage());
		}
		return totalProduct;
	}

	@Override
	public List<ProductDTO> listProduct(PageRequestDTO pageRequestDTO) {
		int end = 0;
		List<ProductDTO> productList = null;
		System.out.println("ProductDaoImpl listProduct() pageRequestDTO-> " + pageRequestDTO);
		ProductDTO productDTO = new ProductDTO();
		// start Row 설정
		productDTO.setStart(pageRequestDTO.getStart());
		// end Row 설정
		productDTO.setEnd(pageRequestDTO.getEnd());
		System.out.println("ProductDaoImpl listProduct() productDTO-> " + productDTO);
		try {
			productList = session.selectList("tkProductListAll", productDTO);
			System.out.println("ProductDaoImpl listProduct() productList.size()-> " + productList.size());
		} catch (Exception e) {
			System.out.println("listProduct() Exception-> "+e.getMessage());
		}
		
		return productList;
	}

}
