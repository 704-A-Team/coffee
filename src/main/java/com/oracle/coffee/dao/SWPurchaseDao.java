package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.PurchaseDto;

public interface SWPurchaseDao {
	int 				purchaseSave(PurchaseDto purchaseDto);
	int 				totalPurchaseCnt();
	List<PurchaseDto> 	purchaseList(PurchaseDto purchaseDto);

}
