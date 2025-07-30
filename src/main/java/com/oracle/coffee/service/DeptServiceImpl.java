package com.oracle.coffee.service;

import java.util.List;
import java.util.Optional;

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
		if(dept.getDept_isdel()==null)dept.changeDept_isdel(false);
		Dept saveDept = deptRepository.deptSave(dept);
		
		return saveDept.getDept_code();
	}

	@Override
	public DeptDto getSingleDept(int dept_code) {
		Dept dept = deptRepository.findByDept_code(dept_code);
		DeptDto singleDept = modelMapper.map(dept, DeptDto.class);
		
		return singleDept;
	}

	@Override
	public DeptDto deptUpdate(DeptDto deptDto) {
		Optional<Dept> updatedDept = deptRepository.findByDept_codeUpdate(deptDto.getDept_code());
		Dept dept = updatedDept.orElseThrow();
		dept.changeDept_name(deptDto.getDept_name());
		dept.changeDept_tel(deptDto.getDept_tel());
	    Dept deptUpdateEntity = deptRepository.deptSave(dept);
		DeptDto deptRtnDto = modelMapper.map(deptUpdateEntity, DeptDto.class );
		
		return deptRtnDto;
    
	}

	@Override
	public void deptDelete(int dept_code) {
		deptRepository.deptDelete(dept_code);
		
	}



}
