package com.oracle.oBootBoard03.dao;

import java.util.List;

import com.oracle.oBootBoard03.dto.DeptDTO;

public interface DeptDao {

	List<DeptDTO> 	deptList(DeptDTO deptDTO);
	int 			totalDept();

}
