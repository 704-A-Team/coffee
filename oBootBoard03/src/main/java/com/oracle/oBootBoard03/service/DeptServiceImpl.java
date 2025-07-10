package com.oracle.oBootBoard03.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oracle.oBootBoard03.dao.DeptDao;
import com.oracle.oBootBoard03.domain.Dept;
import com.oracle.oBootBoard03.dto.DeptDTO;
import com.oracle.oBootBoard03.repository.DeptRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DeptServiceImpl implements DeptService {
	
	private final DeptRepository deptRepository;
	private final DeptDao 		 deptDao;
	private final ModelMapper 	 modelMapper;
	
	@Override
	public int deptSave(DeptDTO deptDTO) {
		// dept 저장
		// dto -> entity 변환
		Dept dept = dtoToEntity(deptDTO);
		Dept dept11 = modelMapper.map(deptDTO, Dept.class);
		Dept saveDept = deptRepository.deptSave(dept);
		int result = 0;
		if(saveDept != null) result = 1;
		return result;
	}
	
	private Dept dtoToEntity(DeptDTO deptDTO) {
		Dept dept = Dept.builder()
						.dept_name(deptDTO.getDept_name())
						.dept_loc(deptDTO.getDept_loc())
						.dept_tel(deptDTO.getDept_tel())
						.build()
						;
		System.out.println("DeptServiceImpl dtoToEntity dept->"+dept);
		return dept;
	}

	@Override
	public int totalDept() {
		System.out.println("DeptServiceImpl totalDept Start");
		int total = deptDao.totalDept();
		// int total = deptRepository.totalDept();
		return total;
	}

	@Override
	public List<DeptDTO> deptList(DeptDTO deptDTO) {
		System.out.println("DeptServiceImpl deptList Start");
		// dto를 Entity 변환 필요한가? -> paging 때문에 MyBatis로 DTO 사용
		// return값 -> dto
		List<DeptDTO> deptList = deptDao.deptList(deptDTO);
		// List<DeptDTO> deptList = deptRepository.findAllDept(deptDTO);
		return deptList;
	}

	@Override
	public DeptDTO deptModify(DeptDTO deptDTO) {
		log.info("deptModify Start");
		Dept deptEntity = deptRepository.deptModify(deptDTO.getDept_code());
		DeptDTO dept = modelMapper.map(deptEntity, DeptDTO.class);
		return dept;
	}

	@Override
	public void deptUpdate(DeptDTO deptDTO) {
		log.info("deptUpdate Start");
		Dept dept = modelMapper.map(deptDTO, Dept.class);
		int result = deptRepository.deptUpdate(dept);
		
	}

	@Override
	public void deptDelete(int dept_code) {
		log.info("deptDelete Start");
		int result = deptRepository.deptDelete(dept_code);
		
	}

	

}
