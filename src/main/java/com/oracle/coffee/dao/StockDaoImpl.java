package com.oracle.coffee.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.stock.MagamPageDto;
import com.oracle.coffee.dto.stock.MagamStatusDto;
import com.oracle.coffee.dto.stock.MonthMagamDto;
import com.oracle.coffee.dto.stock.SilsaDto;
import com.oracle.coffee.dto.stock.StockDto;

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
			total = session.selectOne("totalStock");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public List<StockDto> getStockList(PageRequestDto page) {
		List<StockDto> stocks = null;
		try {
			stocks = session.selectList("stockList", page);
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
	public int magamCheck() {
		return session.selectOne("magamCheck");
	}

	public List<StockDto> getAllStock() {
		List<StockDto> stocks = new ArrayList<>();
		
		try {
			stocks = session.selectList("selectAllStock");
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

	@Override
	public MagamStatusDto getMonthMagam() {
		MagamStatusDto magam = null;

		try {
			magam = session.selectOne("selectMonthMagam");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return magam;
	}

	@Override
	public int totalMonthMagam() {
		int total = 0;
		try {
			total = session.selectOne("totalMonthMagam");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public List<MonthMagamDto> getMonthMagamList(PageRequestDto page) {
		List<MonthMagamDto> magams = null;
		
		try {
			magams = session.selectList("monthMagamList", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return magams;
	}

	@Override
	public int totalMonthMagamPrds(MagamPageDto magamPage) {
		int total = 0;
		try {
			total = session.selectOne("totalMonthMagamPrds", magamPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public List<MonthMagamDto> getMonthMagamPrds(MagamPageDto magamPage) {
		List<MonthMagamDto> magams = null;
		
		try {
			magams = session.selectList("monthMagamPrdList", magamPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return magams;
	}

	@Override
	public void closeMonthMagam() {
		try {
			session.selectOne("closeMonthMagam");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
