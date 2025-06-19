package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.dto.Member1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl implements Member1Dao {
	// mybatis DB 연동
	private final SqlSession session;
	
	
	@Override
	public int memCount(String id) {
		int memCount = 0;
		System.out.println("Member1DaoImpl memCount id->"+id);
		try {
			memCount = session.selectOne("memCount",id);
		} catch (Exception e) {
			System.out.println("Member1DaoImpl memCount Exception->"+e.getMessage());
		}
		
		return memCount;
	}


	@Override
	public List<Member1> listMem() {
		System.out.println("");
		List<Member1> listMem = null;
		try {
			listMem = session.selectList("listMember1");
			System.out.println("Member1DaoImpl listMem listMem.size()-> "+listMem.size());
		} catch (Exception e) {
			System.out.println("Member1DaoImpl listMem Exception->"+e.getMessage());
		}
		return listMem;
	}

}
