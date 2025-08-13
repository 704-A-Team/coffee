package com.oracle.coffee.service;

public interface StockService {
	// 조회시간 기준 마감 상태 테이블에 오늘 날짜의 마감이 없거나 상태가 마감전인 경우 False
	public boolean isClosedMagam();
	
	// 수동 승인 (마감 프로시저 진행 + 마감 상태 테이블 변경)
	public void closeTodayMagam();
	
	// 수동 승인 취소 (마감 취소 프로시저 진행 + 마감 상태 테이블 변경)
	public void cancelTodayMagam();
	
	// 실시간 재고 수량 조회
	public int getRealStock(int productCode);
}
