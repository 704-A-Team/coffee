package com.oracle.coffee.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.oracle.coffee.dto.DeptDto;
import com.oracle.coffee.repository.DeptRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

	private final DeptRepository deptRepository;
	@Override
	public Long totalDept() {
		Long totalCount =  deptRepository.deptTotalcount();
		return totalCount;
	}

	@Override
	public List<DeptDto> deptList(DeptDto deptDto) {
	    List<DeptDto> deptRtnList = deptRepository.findPageDept(deptDto);
	    System.out.println("DeptServiceImpl deptList deptRtnList->"+deptRtnList);	
	    return deptRtnList;
	}

	@Override
	public List<DeptDto> deptAllList() {
		List<DeptDto> listDeptAll  = deptRepository.findAllDept();
		return listDeptAll;
	}

}
