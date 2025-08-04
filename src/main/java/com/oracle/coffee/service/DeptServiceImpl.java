package com.oracle.coffee.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oracle.coffee.domain.Dept;
import com.oracle.coffee.dto.DeptDto;
import com.oracle.coffee.repository.DeptRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

	private final DeptRepository deptRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Long totalDept() {
		Long totalCount =  deptRepository.deptTotalcount();
		
		return totalCount;
	}

	@Override
	public List<DeptDto> deptList(DeptDto deptDto) {
	    List<DeptDto> deptRtnList = deptRepository.findPageDept(deptDto);	
	    
	    return deptRtnList;
	}

	@Override
	public List<DeptDto> deptAllList() {
		List<DeptDto> listDeptAll  = deptRepository.findAllDept();
		
		return listDeptAll;
	}

	@Override
	public int deptSave(DeptDto deptDto) {
		Dept dept = modelMapper.map(deptDto, Dept.class);
		if(dept.getDept_isdel()==1)dept.changeDept_isdel(0);
		Dept saveDept = deptRepository.deptSave(dept);
		
		return saveDept.getDept_code();
	}

	@Override
	public DeptDto getSingleDept(int dept_code) {
			
		return  deptRepository.findByDept_code(dept_code);
	}

	public DeptDto deptUpdate(DeptDto deptDto) {

	    return deptRepository.updateDept(deptDto);
}

	@Override
	public void deptDelete(int dept_code) {
		deptRepository.deptDelete(dept_code);
		
	}



}
