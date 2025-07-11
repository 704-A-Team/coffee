package com.oracle.oBootBoard03.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard03.domain.Emp;
import com.oracle.oBootBoard03.dto.EmpDTO;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2
@RequiredArgsConstructor
public class EmpRepositoryImpl implements EmpRepository {
	
	private final EntityManager em;

	@Override
	public List<Emp> findAllEmp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Emp empSave(Emp emp) {
		log.info("empSave Start");
		em.persist(emp);
		return emp;
	}

	@Override
	public int totalEmp() {
		log.info("totalEmp Start");
		//                               DB 컬럼명이 아니라 "Java 클래스의 필드명"을 기준으로 작성
		Long total = em.createQuery("select Count(e) From Emp e Where e.del_status = false",Long.class).getSingleResult();
		
		return total.intValue();
	}


	
	
}
