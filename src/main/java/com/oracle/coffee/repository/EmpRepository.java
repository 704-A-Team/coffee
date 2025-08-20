package com.oracle.coffee.repository;

import java.util.List;

import com.oracle.coffee.domain.Emp;
import com.oracle.coffee.dto.EmpDto;

public interface EmpRepository {

	Long            empTotalcount(EmpDto empDto);
	List<EmpDto>	findPageEmp(EmpDto empDto);
	List<EmpDto> 	findAllEmp();
	
	Emp 			empSave(Emp emp);
	EmpDto 			findByEmp_code(int emp_code);
	void 			empDelete(int emp_code);
	EmpDto 			updateEmp(EmpDto empDto);
	
	

}