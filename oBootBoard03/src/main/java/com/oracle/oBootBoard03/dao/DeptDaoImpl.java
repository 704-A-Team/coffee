package com.oracle.oBootBoard03.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard03.dto.DeptDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {
	
	private final SqlSession session;

	@Override
	public List<DeptDTO> deptList(DeptDTO deptDTO) {
		List<DeptDTO> deptList = null;
		System.out.println("DeptDaoImpl deptList Start");
		try {
			deptList = session.selectList("deptList",deptDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptList;
	}

	@Override
	public int totalDept() {
		int totalDept = 0;
		System.out.println("DeptDaoImpl totalDept Start");
		try {
			totalDept = session.selectOne("totalDept");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalDept;
	}
	
	

}
