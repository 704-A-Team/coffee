package com.oracle.coffee.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oracle.coffee.domain.Emp;
import com.oracle.coffee.dto.EmpDto;
import com.oracle.coffee.repository.EmpRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	
	private final EmpRepository empRepository;
	private final ModelMapper modelMapper;

	@Override
	public Long totalEmp() {
	Long totalCount =  empRepository.empTotalcount();
		
		return totalCount;
	}

	@Override
	public List<EmpDto> empList(EmpDto empDto) {
	    List<EmpDto> empRtnList = empRepository.findPageEmp(empDto);	
	    
	    return empRtnList;
	}
	
	@Override
	public List<EmpDto> empAllList() {
		List<EmpDto> listEmpAll  = empRepository.findAllEmp();
		
		return listEmpAll;
	}

	@Override
	public int empSave(EmpDto empDto) {
		Emp emp = modelMapper.map(empDto, Emp.class);
		if(emp.getEmp_isdel()==1)emp.changeEmp_isdel(0);
		Emp saveEmp = empRepository.empSave(emp);
		
		return saveEmp.getEmp_code();	
	}

	@Override
	public EmpDto getSingleEmp(int emp_code) {
		
	    return empRepository.findByEmp_code(emp_code); 
	}
	
	@Override
	public EmpDto empUpdate(EmpDto empDto) {

		    return empRepository.updateEmp(empDto);
	}
	
	@Override
	public void empDelete(int emp_code) {
		empRepository.empDelete(emp_code);
		
	}



}