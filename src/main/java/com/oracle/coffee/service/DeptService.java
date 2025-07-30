package com.oracle.coffee.service;	

import java.util.List;

import com.oracle.coffee.dto.DeptDto;

public interface DeptService {

	Long 			totalDept();
	List<DeptDto> 	deptList(DeptDto deptDto);
	List<DeptDto>   deptAllList();
	int 			deptSave(DeptDto deptDto);
	DeptDto 		getSingleDept(int dept_code);
	DeptDto 		deptUpdate(DeptDto deptDto);
	void 			deptDelete(int dept_code);
	



}
