package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.InventoryDto;

public interface InventoryDao {

	int 				totalInventoryDao();
	List<InventoryDto> 	inventoryList(InventoryDto inventoryDto);
	List<InventoryDto> mfgReqList(InventoryDto inventoryDto);

}
