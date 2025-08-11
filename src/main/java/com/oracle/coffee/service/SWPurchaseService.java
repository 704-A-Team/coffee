package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.PurchaseDto;

public interface SWPurchaseService {
	int 				purchaseSave(List<PurchaseDto> purchaseDtoList);
	int 				totalPurchaseCnt(PurchaseDto purchaseDto);
	List<PurchaseDto> 	purchaseList(PurchaseDto purchaseDto);
	PurchaseDto 		purchaseDetail(int purchase_code);
	void 				purchaseApprove(PurchaseDto purchaseApprove);
	void 				purchaseRefuse(PurchaseDto purchaseRefuse);

}
