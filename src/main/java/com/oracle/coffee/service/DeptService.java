package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.DeptDto;

public interface DeptService {

	Long 			totalDept();
	List<DeptDto> 	deptList(DeptDto deptDto);
	List<DeptDto>   deptAllList(); 


}
