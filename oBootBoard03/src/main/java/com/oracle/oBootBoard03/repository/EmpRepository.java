package com.oracle.oBootBoard03.repository;

import java.util.List;

import com.oracle.oBootBoard03.domain.Emp;

public interface EmpRepository {
	List<Emp>		findAllEmp();
	Emp				empSave(Emp emp);
}
