package com.oracle.coffee.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.MagamDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JHMagamDaoImpl implements JHMagamDao {
	
	private final SqlSession sqlSession;
	
	@Override
	public int totalMagamDao() {
		int totMagCnt = 0;
		System.out.println("JHMagamDaoImpl totalMagamDao start...");
		try {
			totMagCnt = sqlSession.selectOne("com.oracle.coffee.MagamMapper.totMagCnt");
			System.out.println("JHMagamDaoImpl totalMagamDao totMagCnt : "+totMagCnt);
		} catch (Exception e) {
			System.out.println("JHMagamDaoImpl totalMagamDao e.getmessage : "+e.getMessage());
		}
		return totMagCnt;
	}

	
	@Override
	public List<MagamDto> magamList(MagamDto magamDto) {
		System.out.println("JHMagamDaoImpl magamList start...");
		
		List<MagamDto> ListMagam = new ArrayList<>();	//초기화
		
		try {
			ListMagam = sqlSession.selectList("com.oracle.coffee.MagamMapper.jhMagAll", magamDto);
			
			if (ListMagam == null) {
				ListMagam = new ArrayList<>();
	            System.out.println("JHMagamDaoImpl returned null from MyBatis, replaced with empty list.");
	        }

	        System.out.println("JHMagamDaoImpl magamList ListMagam.size() : " + ListMagam.size());
		} catch (Exception e) {
			System.out.println("JHMagamDaoImpl magamList e.getmessage : "+e.getMessage());
			e.printStackTrace();
		}
		return ListMagam;
	}

	
	
	
	
	@Override
	public int countMonMagAll(MagamDto magamDto) {
		int countMonMagAll = 0;
		System.out.println("JHMagamDaoImpl totalMagamDao start...");
		try {
			countMonMagAll = sqlSession.selectOne("com.oracle.coffee.MagamMapper.countMonMagAll", magamDto);
			System.out.println("JHMagamDaoImpl totalMagamDao totMagCnt : "+countMonMagAll);
		} catch (Exception e) {
			System.out.println("JHMagamDaoImpl totalMagamDao e.getmessage : "+e.getMessage());
			}
			return countMonMagAll;
		}


	@Override
	public List<MagamDto> monthMagamList(MagamDto magamDto) {
		System.out.println("JHMagamDaoImpl monthMagamList start...");
		
		List<MagamDto> ListMonthMagam = new ArrayList<>();	//초기화
		
		try {
			ListMonthMagam = sqlSession.selectList("com.oracle.coffee.MagamMapper.jhMonMagAll", magamDto);
			
			if (ListMonthMagam == null) {
				ListMonthMagam = new ArrayList<>();
	            System.out.println("JHMagamDaoImpl returned null from MyBatis, replaced with empty list.");
	        }

	        System.out.println("JHMagamDaoImpl magamList ListMonthMagam.size() : " + ListMonthMagam.size());
		} catch (Exception e) {
			System.out.println("JHMagamDaoImpl magamList e.getmessage : "+e.getMessage());
			e.printStackTrace();
		}
		return ListMonthMagam;
	}
	
}

	
	
	
