package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.dto.Dept;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class DeptDaoImpI implements DeptDao {
	// Mybatis DB 연동
	private final SqlSession session;
	
	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		System.out.println("DeptDaoImpI deptSelect Start!");
		try {
			deptList = session.selectList("tkSelectDept");
		} catch (Exception e) {
			System.out.println("DeptDaoImpI deptSelect e.getMessage()" +e.getMessage());
		}
		return deptList;
	}

}
