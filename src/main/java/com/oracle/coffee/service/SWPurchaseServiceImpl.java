package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.ClientMapper;
import com.oracle.coffee.dao.SWPurchaseDao;
import com.oracle.coffee.dto.PurchaseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
//@Transactional
@Log4j2
@RequiredArgsConstructor
public class SWPurchaseServiceImpl implements SWPurchaseService {
	private final SWPurchaseDao		swPurchaseDao;
	private final ClientMapper      clientMapper;

	// 원본 
	@Override
	public int purchaseSave(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseServiceImpl purchaseSave start...");
		
		int purchase_code = swPurchaseDao.purchaseSave(purchaseDto);
		
		return purchase_code;
	}
	
	@Transactional
	@Override
	public int purchaseSavetest(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseServiceImpl purchaseSave start...");
        clientMapper.purchaseSave(purchaseDto);
        System.out.println("SWPurchaseServiceImpl purchaseDto=>"+purchaseDto);
        // 예외 발생 시 전체 롤백
        if (clientMapper.getClass()== null) {
            throw new IllegalArgumentException("purchaseSave not be null");
        }
        purchaseDto.setPurchase_code(1);
        clientMapper.purchaseDetailSave(purchaseDto);		
		return 1;
	}

	@Override
	public int totalPurchaseCnt() {
		System.out.println("SWPurchaseServiceImpl totalPurchaseCnt start...");
		
		int totalPurchaseCnt = swPurchaseDao.totalPurchaseCnt();
		
		return totalPurchaseCnt;
	}

	@Override
	public List<PurchaseDto> purchaseList(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseServiceImpl purchaseList start...");
		
		List<PurchaseDto> purchaseList = swPurchaseDao.purchaseList(purchaseDto);
		System.out.println("SWPurchaseServiceImpl purchaseList purchaseList.size() : " + purchaseList.size());
		
		return purchaseList;
	}
	
	
}
