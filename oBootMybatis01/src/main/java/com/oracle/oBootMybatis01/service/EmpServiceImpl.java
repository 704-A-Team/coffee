package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	private final EmpDao	ed;
	private final DeptDao	dd;
	
	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl totalEmp() start");
		int totEmpCnt = ed.totalEmp();
		System.out.println("EmpServiceImpl totalEmp() totEmpCnt->" +totEmpCnt);
		return totEmpCnt;
	}

	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listEmp start");
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size()->" +empList.size());
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpServiceImpl detailEmp start");
		Emp emp = ed.detailEmp(empno);
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpServiceImpl updateEmp start");
		int updateCount = 0;
		updateCount = ed.updateEmp(emp);
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listManager start");
		empList = ed.listManager();
		return empList;
	}

	@Override
	public List<Dept> deptSelect() {
		System.out.println("EmpServiceImpl deptSelect Start");
		List<Dept> deptList = null;
		deptList = dd.deptSelect();
		System.out.println("EmpServiceImpl deptSelect  deptList.size()->"+deptList.size());
		return deptList;
	}

	@Override
	public int insertEmp(Emp emp) {
		System.out.println("EmpServiceImpl insertEmp Start");
		int insertEmp = 0;
		insertEmp = ed.insertEmp(emp);
		return insertEmp;
	}

	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		System.out.println("EmpServiceImpl deleteEmp Start");
		System.out.println("empno->"+empno);
		try {
			result = ed.deleteEmp(empno);
		} catch (Exception e) {
			System.out.println("EmpServiceImpl deleteEmp e.getMessage()->"+e.getMessage());
		}
		
		return result;
	}

	@Override
	public int condTotalEmp(Emp emp) {
		System.out.println("EmpServiceImpl condTotalEmp Start");
		int totEmpCnt = ed.condTotalEmp(emp);
		System.out.println("EmpServiceImpl condTotalEmp totEmpCnt->"+totEmpCnt);
		return totEmpCnt;
	}

	@Override
	public List<Emp> listSearchEmp(Emp emp) {
		System.out.println("EmpServiceImpl listSearchEmp Start");
		List<Emp> listSearchEmp = null;
		listSearchEmp = ed.listSearchEmp(emp);
		System.out.println("EmpServiceImpl listSearchEmp listSearchEmp.size()->"+listSearchEmp.size());
		return listSearchEmp;
	}


}
