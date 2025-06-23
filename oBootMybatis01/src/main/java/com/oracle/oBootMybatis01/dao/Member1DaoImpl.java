package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.oBootMybatis01.dto.Member1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl implements Member1Dao {
	
	// Mybatis도 Transaction 가능하다
	private final PlatformTransactionManager transactionManager;
	
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
		System.out.println("Member1DaoImpl listMem 스타트");
		List<Member1> listMem = null;
		try {
			listMem = session.selectList("listMember1");
			System.out.println("Member1DaoImpl listMem listMem.size()-> "+listMem.size());
		} catch (Exception e) {
			System.out.println("Member1DaoImpl listMem Exception->"+e.getMessage());
		}
		return listMem;
	}


	@Override
	public int transactionInsertUpdate() {
		System.out.println("Member1DaoImpl transactionInsertUpdate Start");
		int result = 0;
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		try {
			// 두개의 transaction Test 성공과 실패
			// 결과 --> Sqlsession 은 하나 실행 할 때마다 자동 commit
			member1.setId("1005");
			member1.setPassword("2345");
			member1.setName("name1");
			result = session.insert("insertMember1",member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate member1 result-> " + result);
			
			// 같은 PK로 실패유도
			member2.setId("1005");
			member2.setPassword("1234");
			member2.setName("name2");
			result = session.insert("insertMember1",member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate member2 result-> " + result);
		} catch (Exception e) {
			System.out.println("Member1DaoImpl transactionInsertUpdate  Exception-> "+e.getMessage());
			result = -1;
		}
		// member1 -> 성공 , member2 -> 실패
		// 동시 커밋, 롤백 안됨 jpa에서는 transactional 걸어서 자동 관리가 되었으나 Mybatis에서는 안됨
		return result;
	}


	@Override
	public int transactionInsertUpdate3() {
		System.out.println("Member1DaoImpl transactionInsertUpdate3 Start");
		int result = 0;
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		// Transaction 시작
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		
		try {
			// 두개의 transaction Test 성공과 실패
			member1.setId("1007");
			member1.setPassword("2345");
			member1.setName("name1");
			result = session.insert("insertMember1",member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member1 result-> " + result);
			
	
			member2.setId("1008");
			member2.setPassword("1234");
			member2.setName("name2");
			result = session.insert("insertMember1",member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member2 result-> " + result);
			
			// Transaction 끝
			transactionManager.commit(txStatus);
		
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 Exception-> "+e.getMessage());
			result = -1;
		}
		
		return result;
	}

}
