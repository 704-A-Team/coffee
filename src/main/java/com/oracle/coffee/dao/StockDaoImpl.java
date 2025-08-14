package com.oracle.coffee.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.MagamStatusDto;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.SilsaDto;
import com.oracle.coffee.dto.StockDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockDaoImpl implements StockDao {

	private final SqlSession session;
	
	@Override
	public MagamStatusDto getTodayMagam() {
		MagamStatusDto magam = null;
		
		try {
			magam = session.selectOne("selectTodayMagam");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return magam;
	}

	@Override
	public int totalStockCount() {
		int total = 0;
		try {
			total = session.selectOne("InvTotal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public List<StockDto> getStockList(PageRequestDto page) {
		List<StockDto> stocks = null;
		try {
			stocks = session.selectList("InvList", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stocks;
	}

	@Override
	public void closeTodayMagam() {
		try {
			session.selectOne("closeTodayMagam");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cancelTodayMagam() {
		try {
			session.selectOne("cancelTodayMagam");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<StockDto> getAllStock() {
		List<StockDto> stocks = new ArrayList<>();
		
		try {
			stocks = session.selectList("InvAll");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stocks;
	}

	@Override
	public void saveSilsa(List<SilsaDto> silsaList) {
		try {
			session.update("insertSilsa", silsaList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
