package com.oracle.oBootMybatis01.service;

import java.util.HashMap;
import java.util.List;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.DeptVO;
import com.oracle.oBootMybatis01.dto.Emp;
import com.oracle.oBootMybatis01.dto.EmpDept;
import com.oracle.oBootMybatis01.dto.Member1;

public interface EmpService {
	int					totalEmp();
	List<Emp> 			listEmp(Emp emp);
	Emp 				detailEmp(int empno);
	int 				updateEmp(Emp emp);
	List<Emp> 			listManager();
	List<Dept> 			deptSelect();
	int 				insertEmp(Emp emp);
	int 				deleteEmp(int empno);
	int 				condTotalEmp(Emp emp);
	List<Emp> 			listSearchEmp(Emp emp);
	List<EmpDept> 		listEmpDept();
	void 				insertDept(DeptVO deptVO);
	void 				selListDept(HashMap<String, Object> map);
	int 				memCount(String id);
	List<Member1> 		listMem();
	String 				deptName(int deptno);
}
