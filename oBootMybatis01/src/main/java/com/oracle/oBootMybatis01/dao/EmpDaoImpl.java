package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.dto.Emp;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {

	private final SqlSession session;
	
	@Override
	public int totalEmp() {
		int totEmpCount = 0;
		System.out.println("EmpDaoImpl Start totalEmp");
		
		try {
			totEmpCount = session.selectOne("com.oracle.oBootMybatis01.EmpMapper.empTotal");
			System.out.println("EmpDaoImpl totalEmp() totEmpCount->"+totEmpCount);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl totalEmp e.getMessage() -> "+e.getMessage());
		}
		
		return totEmpCount;
	}

	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listEmp start");
		try {
			//								Map ID     , parameter
			empList = session.selectList("tkEmpListAll", emp);
			System.out.println("EmpDaoImpl listEmp empList.size()" + empList.size());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmp e.getMessage() ->"+e.getMessage());
		}
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
		// EMP emp; -->> null 값을 주지 않으면 initialized 에러 발생 --> null로 초기화 해주기
		Emp emp = null;
		System.out.println("EmpDaoImpl detailEmp start");
		try {
			emp = session.selectOne("tkEmpSelOne", empno);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl detailEmp e.getMessage()-> " +e.getMessage());
		}
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpDaoImpl updateEmp Start");
		int updateCount = 0;
		try {
			updateCount = session.update("tkEmpUpdate", emp);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl updateEmp e.getMessage()" + e.getMessage());
		}
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		System.out.println("EmpDaoImpl listManager start");
		List<Emp> empList = null;
		try {
			// emp 관리자만 Select           Naming Rule
			empList = session.selectList("tkSelectManager");
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listManager e.getMessage()->"+e.getMessage());
		}
		return empList;
	}

}
