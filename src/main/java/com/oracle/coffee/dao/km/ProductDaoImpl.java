package com.oracle.coffee.dao.km;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		try {
			session.insert("wanRegister",productDTO);
		} catch (Exception e) {
			System.out.println("wanRegister Exception->"+e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
	}

	@Override
	public void wanImgRegister(List<ProductImgDTO> productImgList) {
		
		System.out.println("DAO wanImgRegister productImgList->"+productImgList);
		try {
			session.insert("wanImgRegister", productImgList);
		} catch (Exception e) {
			System.err.println("상품 이미지 등록 중 오류 발생: " + e.getMessage());
			System.out.println("DAO wanImgRegister e.getMessage()->"+e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		
	}
	
	@Override
	public int priceRegister(ProductPriceDTO priceDTO) {
		int result = 0;
		try {
			result = session.insert("priceRegister", priceDTO);
		} catch (Exception e) {
			System.out.println("priceRegister Exception->"+e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		
		return result;
	}

	@Override
	public void jaegoStatus(ProductWanDTO productWanDTO) {
		LocalDateTime date = productWanDTO.getProduct_reg_date();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
		String month_yrmo = date.format(formatter);
		session.insert("jaegoStatus" , Map.of("MONTH_YRMO" , month_yrmo , "product_code" , productWanDTO.getProduct_code() , "MONTH_MAGAM_REG_CODE" , productWanDTO.getProduct_reg_code()));
		
	}
	
	@Override
	public int countTotal() {
		int countTotal = 0;
		try {
			countTotal = session.selectOne("countTotal");
		} catch (Exception e) {
			System.out.println("countTotal Exception->"+e.getMessage());
		}
		
		return countTotal;
	}

	@Override
	public List<ProductWanDTO> wonList() {
		List<ProductWanDTO> wonList = null;
		try {
			wonList = session.selectList("wonList");
		} catch (Exception e) {
			System.out.println("wonList Exception->"+e.getMessage());
		}
		
		return wonList;
	}

	@Override
	public int findPack(int product_code) {
		int findPack = session.selectOne("findPack" , product_code);
		return findPack;
	}
	
	@Override
	public void saveWeight(int product_wan_code, double weight) {
		session.update("saveWeight" , Map.of("product_code" , product_wan_code , "product_weight" , weight));
		
	}
	
	@Override
	public void wanRecipeSave(RecipeDTO recipe) {
		try {
			session.insert("wanRecipeSave",recipe);
		} catch (Exception e) {
			System.out.println("wanRecipeSave Exception->"+e.getMessage());
			throw e;
		}
		
		
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

	@Override
	public ProductWanDTO getProductImg(int product_code) {
		List<String> productImgList = null;
		ProductWanDTO productWanDTO = new ProductWanDTO();

		try {
			productImgList = session.selectList("productImgList" , product_code);
			productWanDTO.setUploadFileNames(productImgList);

		} catch (Exception e) {
			System.out.println("getProductImg Exception->" + e.getMessage());
		}
		return productWanDTO;
	}

	@Override
	public ProductWanDTO wanModify(ProductWanDTO productDTO) {
		try {
			session.update("wanModify" , productDTO);
			System.out.println("wanModify After->" + productDTO);
		} catch (Exception e) {
			System.out.println("wanModify Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		return productDTO;
	}

	@Override
	public ProductWanDTO delImgModify(ProductWanDTO productDTO) {
		try {
			session.delete("delImgModify", productDTO);
		} catch (Exception e) {
			System.out.println("delImgModify Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		return productDTO;
	}

	@Override
	public void recipeDel(int pdwan_code) {
		try {
			session.delete("recipeDel" , pdwan_code);
		} catch (Exception e) {
			System.out.println("recipeDel Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		
	}

	@Override
	public void productDelUpdate(ProductWanDTO productWanDTO) {
		try {
			session.update("productDelUpdate", productWanDTO);
		} catch (Exception e) {
			System.out.println("productDelUpdate Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		
	}


	@Override
	public void priceBeforeEnd(ProductPriceDTO productPriceDTO) {
		try {
			System.out.println("dao priceBeforeEnd productPriceDTO->" + productPriceDTO);
			session.update("priceBeforeEnd" , productPriceDTO);
		} catch (Exception e) {
			System.out.println("priceBeforeEnd Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		
	}

	@Override
	public ProductPriceDTO prePrice(ProductPriceDTO priceDTO) {
		ProductPriceDTO prePrice = null;
		System.out.println("dao prePrice->" + priceDTO);
		try {
			prePrice = session.selectOne("prePrice" , priceDTO);
		} catch (Exception e) {
			System.out.println("prePrice Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		
		return prePrice;
	}

	@Override
	public void priceAfterStart(ProductPriceDTO priceDTO) {
		try {
			session.insert("priceAfterStart" , priceDTO);
		} catch (Exception e) {
			System.out.println("priceAfterStart Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		
	}

	@Override
	public List<ProductWanDTO> mfgWanList() {
		List<ProductWanDTO> mfgWanList = null;
		try {
			mfgWanList = session.selectList("mfgWanList");
		} catch (Exception e) {
			System.out.println("mfgWanList Exception->" + e.getMessage());
			throw e;  // 예외를 다시 던져줘야 트랜잭션이 감지함
		}
		return mfgWanList;
	}

	@Override
	public List<ProductPriceDTO> priceHistory(int product_code) {
		List<ProductPriceDTO> priceHistory = null;
		try {
			priceHistory = session.selectList("priceHistory" , product_code);
		} catch (Exception e) {
			System.out.println("priceHistory Exception->" + e.getMessage());
		}
		return priceHistory;
	}

	@Override
	public double launchPrice(int product_code) {
		// Map<String, Object> param = new HashMap<>();
		// param.put("product_code" , product_code);
		double tot_wan_price =  session.selectOne("launchPrice" , product_code);
		System.out.println("ProductDao launchPrice product_code->"+product_code);
		return tot_wan_price;
	}

	
	public int launchPrice3(int product_code) {
		Map<String, Object> param = new HashMap<>();
		param.put("product_code" , product_code);
		session.selectOne("launchPrice" , param);
		System.out.println("ProductDao launchPrice param->"+param);
		return (int) param.get("tot_wan_price");
	}
	
	@Override
	public void wanPriceBeforeModify(ProductPriceDTO priceDTO) {
		LocalDateTime end_date = priceDTO.getStart_date().minusSeconds(1);
		priceDTO.setEnd_date(end_date);
		session.update("wanPriceBeforeModify" , priceDTO);
		
	}
	
	@Override
	public void wanPriceModify(ProductPriceDTO priceDTO) {
		session.insert("wanPriceModify" , priceDTO);
		
	}









}
