package com.oracle.oBootBoard03.repository;

import java.util.List;

import com.oracle.oBootBoard03.domain.Dept;
import com.oracle.oBootBoard03.dto.DeptDTO;

public interface DeptRepository {
	List<DeptDTO>	findAllDept(DeptDTO deptDTO);
	Dept			deptSave(Dept dept);
	int 			totalDept();
	Dept 			deptModify(int dept_code);
	int 			deptUpdate(Dept dept);
	int 			deptDelete(int dept_code);
}
