package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.MagamStatusDto;
import com.oracle.coffee.dto.PageRequestDto;
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
	public int totalCount() {
		int total = 0;
		try {
			total = session.selectOne("InvTotal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public List<StockDto> list(PageRequestDto page) {
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

}
