package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.InventoryDto;

public interface JHservice {
	List<InventoryDto> 	inventoryList(InventoryDto inventoryDto);
	int 				totalInventory();
}
