package com.oracle.coffee.repository;

import java.util.List;

import com.oracle.coffee.domain.Dept;
import com.oracle.coffee.dto.DeptDto;

public interface DeptRepository {

	Long				deptTotalcount();
	List<DeptDto> 		findPageDept(DeptDto deptDto);
	List<DeptDto> 		findAllDept();
	Dept 				deptSave(Dept dept);
	DeptDto 			findByDept_code(int dept_code);
	void 				deptDelete(int dept_code);
	DeptDto 			updateDept(DeptDto deptDto);

}
