package com.oracle.coffee.dao;

import java.util.List;

import com.oracle.coffee.dto.InventoryDto;

public interface InventoryDao {

	int 				totalInventoryDto();
	List<InventoryDto> 	inventoryList(InventoryDto inventoryDto);

}
