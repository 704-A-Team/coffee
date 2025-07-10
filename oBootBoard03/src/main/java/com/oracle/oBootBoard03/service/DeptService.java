package com.oracle.oBootBoard03.service;

import java.util.List;

import com.oracle.oBootBoard03.dto.DeptDTO;

public interface DeptService {
	int				deptSave(DeptDTO deptDTO);
	int				totalDept();
	List<DeptDTO>	deptList(DeptDTO deptDTO);
	DeptDTO 		deptModify(DeptDTO deptDTO);
	void 			deptUpdate(DeptDTO deptDTO);
	void 			deptDelete(int dept_code);
	
}
