package com.oracle.oBootBoard03.service;

import java.util.List;

import com.oracle.oBootBoard03.dto.EmpDTO;

public interface EmpService {
	int				totalEmp();
	List<EmpDTO>	empList(EmpDTO empDTO);
	int 			empSave(EmpDTO empDTO);
}
