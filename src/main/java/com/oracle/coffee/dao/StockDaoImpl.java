package com.oracle.coffee.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.MagamStatusDto;

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

}
