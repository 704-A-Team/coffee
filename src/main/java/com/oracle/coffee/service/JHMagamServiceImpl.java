package com.oracle.coffee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.coffee.dao.JHMagamDao;
import com.oracle.coffee.dto.MagamDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JHMagamServiceImpl implements JHMagamService {
	
	private final JHMagamDao jhMagamDao;

	@Override
	public int totalMagamDto() {
		System.out.println("JHMagamServiceImpl totalMagamDto start...");
		
		int totMagCnt = jhMagamDao.totalMagamDao();
		System.out.println("JHMagamServiceImpl totalMagamDto totMagCnt : "+totMagCnt);
		return totMagCnt;
	}

	
	
	
	@Override
	public List<MagamDto> magamList(MagamDto magamDto) {
		System.out.println("JHMagamServiceImpl magamList start...");
		
		List<MagamDto> ListMagam = jhMagamDao.magamList(magamDto);
		
		// 방어 코드 추가
	    if (ListMagam == null) {
	    	ListMagam = new ArrayList<>();
	        System.out.println("JHMagamServiceImpl magamList returned null, replaced with empty list.");
	    }
	    
		System.out.println("JHMagamServiceImpl magamList ListMagam.size : "+ListMagam.size());
		return ListMagam;
	}

	
	
	
	@Override
	public List<MagamDto> monthMagamList(MagamDto magamDto) {
		System.out.println("JHMagamServiceImpl monthMagamList start...");
		
		List<MagamDto> ListMonthMagam = jhMagamDao.monthMagamList(magamDto);
		
		// 방어 코드 추가
	    if (ListMonthMagam == null) {
	    	ListMonthMagam = new ArrayList<>();
	        System.out.println("JHMagamServiceImpl magamList returned null, replaced with empty list.");
	    }
	    
		System.out.println("JHMagamServiceImpl magamList ListMonthMagam.size : "+ListMonthMagam.size());
		return ListMonthMagam;
	}

	
	
	
	@Override
	public int countMonMagAll(MagamDto magamDto) {
		System.out.println("JHMagamServiceImpl countMonMagAll start...");
		
		int countMonMagAll = jhMagamDao.countMonMagAll(magamDto);
		System.out.println("JHMagamServiceImpl totalMagamDto countMonMagAll : "+countMonMagAll);
		return countMonMagAll;
	}
	
	

}
