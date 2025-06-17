package com.oracle.oBootMybatis01.service;

import java.util.List;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.Emp;

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
}
