package com.oracle.coffee.dao;

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
	public int totalInventoryDto() {
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
		List<InventoryDto> listInventory = null;
		System.out.println("InventoryDaoImpl inventoryList start...");
		
		try {
			listInventory = sqlSession.selectList("jhInvAll", inventoryDto);
			System.out.println("InventoryDaoImpl inventoryList listInventory.size() : "+listInventory.size());
		} catch (Exception e) {
			System.out.println("InventoryDaoImpl inventoryList e.getmessage"+e.getMessage());
		}
		return listInventory;
	}

}
