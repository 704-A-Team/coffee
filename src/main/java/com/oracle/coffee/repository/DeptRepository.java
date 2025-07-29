package com.oracle.coffee.repository;

import java.util.List;

import com.oracle.coffee.dto.DeptDto;

public interface DeptRepository {

	Long deptTotalcount();
	List<DeptDto> findPageDept(DeptDto deptDto);
	List<DeptDto> findAllDept();

}
