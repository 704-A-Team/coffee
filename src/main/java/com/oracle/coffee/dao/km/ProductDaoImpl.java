package com.oracle.coffee.dao.km;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.km.ProductDTO;
import com.oracle.coffee.dto.km.ProductImgDTO;
import com.oracle.coffee.dto.km.ProductPriceDTO;
import com.oracle.coffee.dto.km.RecipeDTO;
import com.oracle.coffee.dto.km.WanAndRecipeDTO;

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
	public void wanImgRegister(List<ProductImgDTO> productImgList) {
		
		System.out.println("DAO wanImgRegister productImgList->"+productImgList);
		try {
			session.insert("wanImgRegister", productImgList);
		} catch (Exception e) {
			e.getMessage();
			System.err.println("상품 이미지 등록 중 오류 발생: " + e.getMessage());
			System.out.println("DAO wanImgRegister e.getMessage()->"+e.getMessage());
		}
		
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

	@Override
	public void wanRecipeSave(RecipeDTO recipe) {
		session.insert("wanRecipeSave",recipe);
		
	}

	@Override
	public List<ProductDTO> wanList(ProductDTO productDTO) {
		List<ProductDTO> wanList = session.selectList("wanList", productDTO);
		for(ProductDTO pDto : wanList) {
			System.out.println("wanList->"+pDto);
		}
		return wanList;
	}

	@Override
	public List<WanAndRecipeDTO> wanProductModifyInForm(int product_code) {
		List<WanAndRecipeDTO> wanModifyDTO = null;
		try {
			wanModifyDTO = session.selectList("wanAndRcpModify" , product_code);
			System.out.println("wanAndRcpModify->"+wanModifyDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return wanModifyDTO;
	}


}
