package com.oracle.oBootBoard03.repository;

import java.util.List;

import com.oracle.oBootBoard03.domain.Emp;
import com.oracle.oBootBoard03.dto.EmpDTO;

public interface EmpRepository {
	List<Emp>		findAllEmp();
	Emp 			empSave(Emp emp);
	int 			totalEmp();

}
