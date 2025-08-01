package com.oracle.coffee.dao;

import org.apache.ibatis.annotations.Mapper;

import com.oracle.coffee.dto.PurchaseDto;

@Mapper
public interface ClientMapper {
	 void purchaseSave(PurchaseDto purchaseDto);
	 void purchaseDetailSave(PurchaseDto purchaseDto);

}
 