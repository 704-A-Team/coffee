package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.SilsaDto;
import com.oracle.coffee.dto.StockDto;

public interface StockService {
	// 재고 정보 페이징 리스트
	public PageRespDto<StockDto, Paging> list(PageRequestDto page);
	
	// 조회시간 기준 마감 상태 테이블에 오늘 날짜의 마감이 없거나 상태가 마감전인 경우 False
	public boolean isClosedMagam();
	
	// 수동 승인 (마감 프로시저 진행 + 마감 상태 테이블 변경)
	public void closeTodayMagam() throws Exception;
	
	// 수동 승인 취소 (마감 취소 프로시저 진행 + 마감 상태 테이블 변경)
	public void cancelTodayMagam() throws Exception;
	
	// 실시간 재고 수량 조회
	public int getRealStock(int productCode);

	// 모든 재고 정보
	public List<StockDto> getAll();

	// 재고 조정
	public void saveSilsa(List<SilsaDto> silsaList, int empCode);


}
