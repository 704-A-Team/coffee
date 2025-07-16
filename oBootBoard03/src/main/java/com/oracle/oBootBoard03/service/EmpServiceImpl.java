package com.oracle.oBootBoard03.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oracle.oBootBoard03.dao.EmpDao;
import com.oracle.oBootBoard03.domain.Emp;
import com.oracle.oBootBoard03.domain.EmpImage;
import com.oracle.oBootBoard03.dto.EmpDTO;
import com.oracle.oBootBoard03.repository.EmpRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class EmpServiceImpl implements EmpService {
	
	private final EmpDao 		empDao;
	private final EmpRepository empRepository;
	private final ModelMapper 	modelMapper;

	@Override
	public int totalEmp() {
		int total = empRepository.totalEmp();
		return total;
	}

	@Override
	public List<EmpDTO> empList(EmpDTO empDTO) {
		log.info("empList Start");
	//	List<EmpDTO> empList = empDao.empList(empDTO);
		List<EmpDTO> empList1 = empRepository.empList1(empDTO);
		log.info("Repository use empList1->"+empList1 );
		System.out.println("이미지 경로 확인: /upload/" + empDTO.getSimage());
		System.out.println("파일 실제 위치 확인: C:/spring/springSrc17/oBootBoard03/upload/" + empDTO.getSimage());
		// Repository use
		return empList1;
	}

	@Override
	public int empSave(EmpDTO empDTO) {
		log.info("empSave Start");
		//Emp emp = modelMapper.map(empDTO, Emp.class);
		// modelMapper 쓰려고 보니까 file 필드가 안맞아서 이미지 업로드 안되겠구나
		Emp emp = dtoToEntity(empDTO);
		
		Emp saveEmp = empRepository.empSave(emp);
		
		return saveEmp.getEmp_no();
		
	}

	private Emp dtoToEntity(EmpDTO empDTO) {
		
		Emp emp = Emp.builder()
				 .emp_id(empDTO.getEmp_id())
				 .emp_password(empDTO.getEmp_password())
				 .emp_name(empDTO.getEmp_name())
				 .email(empDTO.getEmail())
				 .emp_tel(empDTO.getEmp_tel())
				 .sal(empDTO.getSal())
				 .dept_code(empDTO.getDept_code())
				 .in_date(empDTO.getIn_date())
				 .build()
				 ;
	
		List<String> uploadfileNames = empDTO.getUploadFileNames();
		if(uploadfileNames == null) return emp;
		
		uploadfileNames.stream()
					   .forEach(file->emp.imageAddString(file))
					   ;
		
		return emp;
	}

	@Override
	public EmpDTO detail(int emp_no) {
		EmpDTO empDTO = empRepository.detail(emp_no);

	//	EmpDTO empDTO = EntityToDto(emp);
		return empDTO;
	}

	

	@Override
	public void delete(int emp_no) {
		log.info("delete Start");
		empRepository.delete(emp_no);
		
		
	}

}
