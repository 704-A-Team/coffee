package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.ClientDao;
import com.oracle.coffee.dao.SWProductDao;
import com.oracle.coffee.dao.ProvideDao;
import com.oracle.coffee.dto.SWClientDto;
import com.oracle.coffee.dto.ProductDto;
import com.oracle.coffee.dto.ProvideDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ProvideServiceImpl implements ProvideService {
	private final ProvideDao 	provideDao;
	private final SWProductDao 	productDao;
	private final ClientDao	 	clientDao;


	@Override
	public List<ProductDto> getProductInfo(int product_type) {
		System.out.println("ProvideServiceImpl getProductInfo start...");
		
		List<ProductDto> productList = productDao.getProductInfo(product_type);
		
		return productList;
	}

	@Override
	public List<SWClientDto> getClientInfo(int client_type) {
		System.out.println("ProvideServiceImpl getClientInfo start...");
		
		List<SWClientDto> clientList = clientDao.getClientInfo(client_type);
		
		return clientList;
	}

	@Override
	public int provideSave(ProvideDto provideDto) {
		System.out.println("ProvideServiceImpl provideSave start...");
		
		int provide_code = provideDao.provideSave(provideDto);
		
		return provide_code;
	}

	@Override
	public int totalProvide(ProvideDto provideDto) {
		System.out.println("ProvideServiceImpl totalProvide start...");
		
		int totalProvideCount = provideDao.totalProvide(provideDto);
		
		return totalProvideCount;
	}

	@Override
	public List<ProvideDto> provideList(ProvideDto provideDto) {
		System.out.println("ProvideServiceImpl provideList start...");
		
		List<ProvideDto> provideList = provideDao.provideList(provideDto);
		
		return provideList;
	}

	@Override
	public ProvideDto provideDetail(int provide_code) {
		System.out.println("ProvideServiceImpl provideDetail start...");
		
		return provideDao.provideDetail(provide_code);
	}

	@Override
	public int provideModify(ProvideDto provideDto) {
		System.out.println("ProvideServiceImpl provideModify start...");
		
		return provideDao.provideModify(provideDto);
	}

	@Override
	public void provideDelete(int provide_code) {
		System.out.println("ProvideServiceImpl provideDelete start...");
		
		ProvideDto provideDetail = provideDao.provideDetail(provide_code);
		provideDetail.setProvide_isdel(1);;
		
		provideDao.provideDelete(provideDetail);
	}

	@Override
	public ProvideDto getProvideInfo(ProvideDto provideDto) {
		System.out.println("ProvideServiceImpl getProvideInfo start...");
		
		return provideDao.getProvideInfo(provideDto);
	}

	@Override
	public List<ProvideDto> getProvideByProduct(int product_code) {
		System.out.println("ProvideServiceImpl getProvideByProduct start...");
		
		return provideDao.getProvideByProduct(product_code);
	}

	@Override
	public List<ProvideDto> getProductsByClient(int client_code) {
		System.out.println("ProvideServiceImpl getProductsByClient start...");
		
		return provideDao.getProductsByClient(client_code);
	}


	
}
