package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.InventoryDto;

public interface JHservice {
	List<InventoryDto> 	inventoryList(InventoryDto inventoryDto);
	int 				totalInventory();
	List<InventoryDto> mfgReqList(InventoryDto inventoryDto);
	
	
	
/////////////////////////////////////////////////////////////////////////////////////
//	민영님이 부탁하신 것: 현재 마감상태(마감/비마감)인지 상태구분
//	boolean MagamGap(boolean isMagam);
/////////////////////////////////////////////////////////////////////////////////////
	
}
