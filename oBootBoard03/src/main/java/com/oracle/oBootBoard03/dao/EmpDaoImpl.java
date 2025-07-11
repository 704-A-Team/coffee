package com.oracle.oBootBoard03.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard03.dto.EmpDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
	
	private final SqlSession session;

	@Override
	public List<EmpDTO> empList(EmpDTO empDTO) {
		log.info("empList Start");
		List<EmpDTO> empList = null;
		
		try {
			empList = session.selectList("empList", empDTO);
			log.info("empList: "+empList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}


}
