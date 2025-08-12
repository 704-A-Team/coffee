package com.oracle.coffee.service;

import java.util.ArrayList;
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

	
	
	
/////////////////////////////////////////////////////////////////////////////////////
// 민영님이 부탁하신 것: 현재 마감상태(마감/비마감)인지 상태구분
//	@Override
//	public boolean MagamGap() {
//		System.out.println("JHserviceImpl MagamGap start...");
//		boolean MagamGapSev = inventoryDao.MagamGapDao();
//		System.out.println("JHserviceImpl MagamGap MagamGapSev : "+MagamGapSev);
//		return MagamGapSev;
//	}
/////////////////////////////////////////////////////////////////////////////////////


}
