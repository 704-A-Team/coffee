package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.SWPurchaseDao;
import com.oracle.coffee.dto.PurchaseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SWPurchaseServiceImpl implements SWPurchaseService {
	private final SWPurchaseDao		swPurchaseDao;

	@Override
	public int purchaseSave(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseServiceImpl purchaseSave start...");
		
		int purchase_code = swPurchaseDao.purchaseSave(purchaseDto);
		
		return purchase_code;
	}
	@Override
	public int totalPurchaseCnt(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseServiceImpl totalPurchaseCnt start...");
		
		int totalPurchaseCnt = swPurchaseDao.totalPurchaseCnt(purchaseDto);
		
		return totalPurchaseCnt;
	}

	@Override
	public List<PurchaseDto> purchaseList(PurchaseDto purchaseDto) {
		System.out.println("SWPurchaseServiceImpl purchaseList start...");
		
		List<PurchaseDto> purchaseList = swPurchaseDao.purchaseList(purchaseDto);
		System.out.println("SWPurchaseServiceImpl purchaseList purchaseList.size() : " + purchaseList.size());
		
		return purchaseList;
	}
	@Override
	public PurchaseDto purchaseDetail(int purchase_code) {
		System.out.println("SWPurchaseServiceImpl purchaseDetail start...");
		
		PurchaseDto purchaseDetail = swPurchaseDao.purchaseDetail(purchase_code);
		System.out.println("SWPurchaseServiceImpl purchaseDetail : " + purchaseDetail);
		
		return purchaseDetail;
	}
	
	
}
