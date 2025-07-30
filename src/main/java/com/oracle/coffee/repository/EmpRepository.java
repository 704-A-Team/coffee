package com.oracle.coffee.repository;

import java.util.List;
import java.util.Optional;

import com.oracle.coffee.domain.Emp;
import com.oracle.coffee.dto.EmpDto;

public interface EmpRepository {

	Long 			empTotalcount();
	List<EmpDto>	findPageEmp(EmpDto empDto);
	List<EmpDto> 	findAllEmp();
	Emp 			empSave(Emp emp);
	Emp 			findByEmp_code(int emp_code);
	Optional<Emp> 	findByEmp_codeUpdate(int emp_code);
	void 			empDelete(int emp_code);

}
