package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.DeptVO;

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

	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("DeptDaoImpI insertDept Start!");
		try {
			session.selectOne("procDeptInsert", deptVO);
												// call by reference 객체이므로 값이 저장된다
		} catch (Exception e) {
			System.out.println("DeptDaoImpI insertDept e.getMessage()" +e.getMessage());
		}
	}

	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("DeptDaoImpI selListDept Start!");
		try {
			// ResultMap은 DB 컬럼명과 DTO의 변수 명이 다를 때 사용
			// 프로시저는 map의 주소(콜바이레퍼런스)에 LIST로 담겨진다
			// 프로시저는 그래서 session.selectList 의미없다
			session.selectOne("procDeptList",map);
		} catch (Exception e) {
			System.out.println("DeptDaoImpI selListDept e.getMessage()->"+e.getMessage());
		}
	}

}
