package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.stock.MagamPageDto;
import com.oracle.coffee.dto.stock.MonthMagamDto;
import com.oracle.coffee.dto.stock.SilsaDto;
import com.oracle.coffee.dto.stock.StockDto;

public interface StockService {
	// 재고 정보 페이징 리스트
	public PageRespDto<StockDto, Paging> getStockList(PageRequestDto page);
	
	// 오늘 기준 월마감인 경우 True
	public boolean isClosedMonth();
	
	// 조회시간 기준 마감 상태 테이블에 오늘 날짜의 마감이 없거나 상태가 마감전인 경우 False
	public boolean isClosedToday();
	
	// 일마감 + 월마감
	public boolean isClosedMagam();
	
	// 수동 일마감 (마감 프로시저 진행 + 마감 상태 테이블 변경)
	public void closeTodayMagam() throws Exception;
	
	// 수동 일마감 취소 (마감 취소 프로시저 진행 + 마감 상태 테이블 변경)
	public void cancelTodayMagam() throws Exception;
	
	// 마감상태 조회
	public int magamCheck();

	// 모든 재고 정보
	public List<StockDto> getAllStock();

	// 재고 조정
	public void saveSilsa(List<SilsaDto> silsaList, int empCode) throws Exception;

	// 월마감 리스트
	public PageRespDto<MonthMagamDto, Paging> getMonthMagams(PageRequestDto page);

	// 월별 마감 제품 리스트
	public PageRespDto<MonthMagamDto, Paging> getMonthMagamProducts(PageRequestDto page, MagamPageDto magamPage);

	// 수동 월마감
	public void closeMonthMagam() throws Exception;
	
	// 오늘자 실사 목록
	public List<SilsaDto> getTodaySilsa();

	// 과거 실사 목록
	public PageRespDto<SilsaDto, Paging> getSilsaList(PageRequestDto page);

}
