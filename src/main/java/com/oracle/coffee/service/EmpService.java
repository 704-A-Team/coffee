package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.EmpDto;

public interface EmpService {

	Long 				totalEmp();
	List<EmpDto> 		empList(EmpDto empDto);
	List<EmpDto>   		empAllList();
	
	int 				empSave(EmpDto empDto);
	EmpDto 				getSingleEmp(int emp_code);
	EmpDto 				empUpdate(EmpDto empDto);
	void 				empDelete(int emp_code);

}
