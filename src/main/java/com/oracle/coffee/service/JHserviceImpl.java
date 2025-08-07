package com.oracle.coffee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.InventoryDao;
import com.oracle.coffee.dto.InventoryDto;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JHserviceImpl implements JHservice {
	
	private final InventoryDao inventoryDao;
	private final EntityManager em;

	@Override
	public int totalInventory() {
		System.out.println("JHserviceImpl totalInventoryDto start...");
		
		int totInvCnt = inventoryDao.totalInventoryDao();
		System.out.println("JHserviceImpl totalInventoryDto totInvCnt"+totInvCnt);
		return totInvCnt;
	}

	@Override
	public List<InventoryDto> inventoryList(InventoryDto inventoryDto) {
	    System.out.println("JHserviceImpl inventoryList start...");

	    List<InventoryDto> listInventory = inventoryDao.inventoryList(inventoryDto);

	    // 방어 코드 추가
	    if (listInventory == null) {
	        listInventory = new ArrayList<>();
	        System.out.println("JHserviceImpl inventoryList returned null, replaced with empty list.");
	    }
	    System.out.println("JHserviceImpl inventoryList listInventory.size : " + listInventory.size());
	    return listInventory;
	}
	
	@Override
	public List<InventoryDto> mfgReqList(InventoryDto inventoryDto) {
		System.out.println("JHserviceImpl mfgReqList start...");
		
		List<InventoryDto> listMfgReq = inventoryDao.mfgReqList(inventoryDto);
		
		if (listMfgReq == null) {
			listMfgReq = new ArrayList<>();
	        System.out.println("JHserviceImpl mfgReqList returned null, replaced with empty list.");
	    }
		System.out.println("JHserviceImpl mfgReqList listmfgReq.size : " + listMfgReq.size());
		return listMfgReq;
	}

}
