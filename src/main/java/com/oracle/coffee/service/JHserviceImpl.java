package com.oracle.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.InventoryDao;
import com.oracle.coffee.dto.InventoryDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JHserviceImpl implements JHservice {
	
	private final InventoryDao inventoryDao;

	@Override
	public int totalInventoryDto() {
		System.out.println("JHserviceImpl totalInventoryDto start...");
		int totInvCnt = inventoryDao.totalInventoryDto();
		System.out.println("JHserviceImpl totalInventoryDto totInvCnt"+totInvCnt);
		return totInvCnt;
	}

	@Override
	public List<InventoryDto> inventoryList(InventoryDto inventoryDto) {
		List<InventoryDto> listInventory = null;
		System.out.println("JHserviceImpl inventoryList start...");
		
		listInventory = inventoryDao.inventoryList(inventoryDto);
		System.out.println("JHserviceImpl inventoryList listInventory.size : "+listInventory.size());
		return null;
	}

}
