package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.PurchaseDto;

public interface SWPurchaseService {
	int 				purchaseSave(PurchaseDto purchaseDto);
	int 				totalPurchaseCnt();
	List<PurchaseDto> 	purchaseList(PurchaseDto purchaseDto);

}
