package com.oracle.coffee.dao.km;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.km.ProductWanDTO;
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
	public void wanRegister(ProductWanDTO productDTO) {
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
	public List<ProductWanDTO> wonList() {
		List<ProductWanDTO> wonList = session.selectList("wonList");
		return wonList;
	}

	@Override
	public void wanRecipeSave(RecipeDTO recipe) {
		session.insert("wanRecipeSave",recipe);
		
	}

	@Override
	public List<ProductWanDTO> wanList(ProductWanDTO productDTO) {
		List<ProductWanDTO> wanList = session.selectList("wanList", productDTO);
		for(ProductWanDTO pDto : wanList) {
			System.out.println("wanList->"+pDto);
		}
		return wanList;
	}

	@Override
	public WanAndRecipeDTO wanAndRcpDetailInForm(int product_code) {
		WanAndRecipeDTO wanAndRcpDTO = null;
		try {
			wanAndRcpDTO = session.selectOne("wanAndRcpDetail" , product_code);
			System.out.println("wanAndRcpDetail->"+wanAndRcpDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return wanAndRcpDTO;
	}

	@Override
	public WanAndRecipeDTO wanModifyInForm(int product_code) {
		WanAndRecipeDTO wanModifyDTO1 = session.selectOne("wanModifyInForm" , product_code);
		System.out.println("wanModifyDTO1->" + wanModifyDTO1);
		return wanModifyDTO1;
	}


}
