package com.oracle.coffee.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.InventoryDto;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class InventoryDaoImpl implements InventoryDao {
	
	private final SqlSession sqlSession;

	@Override
	public int totalInventoryDao() {
		int totInvCnt = 0;
		System.out.println("InventoryDaoImpl totalInventoryDto start...");
		try {
			totInvCnt = sqlSession.selectOne("com.oracle.coffee.InventoryMapper.InvTotal");
			System.out.println("InventoryDaoImpl totalInventoryDto totInvCnt"+totInvCnt);
		} catch (Exception e) {
			System.out.println("InventoryDaoImpl totalInventoryDto e.getmessage : "+e.getMessage());
		}
		return totInvCnt;
	}

	@Override
	public List<InventoryDto> inventoryList(InventoryDto inventoryDto) {
	    System.out.println("InventoryDaoImpl inventoryList start...");

	    List<InventoryDto> listInventory = new ArrayList<>(); // 초기화

	    try {
	    	listInventory = sqlSession.selectList("com.oracle.coffee.InventoryMapper.jhInvAll", inventoryDto);

	        if (listInventory == null) {
	            listInventory = new ArrayList<>();
	            System.out.println("InventoryDaoImpl returned null from MyBatis, replaced with empty list.");
	        }

	        System.out.println("InventoryDaoImpl inventoryList listInventory.size() : " + listInventory.size());
	    } catch (Exception e) {
	        System.out.println("InventoryDaoImpl inventoryList e.getMessage: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return listInventory;
	}

	@Override
	public List<InventoryDto> mfgReqList(InventoryDto inventoryDto) {
		System.out.println("InventoryDaoImpl mfgReqList start...");
		
		List<InventoryDto> listMfgReq = new ArrayList<>(); // 초기화
		
		try {
			listMfgReq = sqlSession.selectList("com.oracle.coffee.InventoryMapper.jhMfgReqAll", inventoryDto);
			
			if (listMfgReq == null) {
				listMfgReq = new ArrayList<>();
	            System.out.println("InventoryDaoImpl returned null from MyBatis, replaced with empty list.");
	        }

	        System.out.println("InventoryDaoImpl inventoryList listMfgReq.size() : " + listMfgReq.size());
		} catch (Exception e) {
			System.out.println("InventoryDaoImpl mfgReqList e.getMessage: " + e.getMessage());
	        e.printStackTrace();
		}
		
		return listMfgReq;
	}

}
