package com.oracle.coffee.dao.km;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.km.ProductDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
	
	private final SqlSession session;

	@Override
	public void wanRegister(ProductDTO productDTO) {
		session.insert("wanRegister",productDTO);
	}

	@Override
	public int priceRegister(ProductPriceDTO priceDTO) {
		int result = session.insert("priceRegister", priceDTO);
		return result;
	}

	@Override
	public int countTotal() {
		int countTotal = session.selectOne("countTotal");
		return countTotal;
	}

	@Override
	public List<ProductDTO> wonList() {
		List<ProductDTO> wonList = session.selectList("wonList");
		return wonList;
	}

}
