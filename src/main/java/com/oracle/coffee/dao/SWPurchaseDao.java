package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.PurchaseDto;

public interface SWPurchaseDao {
	int 				purchaseSave(PurchaseDto purchaseDto);
	int 				totalPurchaseCnt(PurchaseDto purchaseDto);
	List<PurchaseDto> 	purchaseList(PurchaseDto purchaseDto);
	PurchaseDto 		purchaseDetail(int purchase_code);

}
