package com.oracle.oBootBoard03.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.oBootBoard03.domain.Emp;
import com.oracle.oBootBoard03.dto.DeptDTO;
import com.oracle.oBootBoard03.dto.EmpDTO;
import com.oracle.oBootBoard03.service.DeptService;
import com.oracle.oBootBoard03.service.EmpService;
import com.oracle.oBootBoard03.service.Paging;
import com.oracle.oBootBoard03.util.CustomFileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/emp")
public class EmpController {
	
	private final EmpService 		empService;
	private final DeptService 		deptService;
	private final CustomFileUtil 	utilFile;
	
	// 입력 폼
	@GetMapping("/empInForm")
	public String empInForm(Model model) {
		System.out.println("emp/empInForm Start");
		// 재사용성을 위해 C-> deptService 부름
		// 목적 : JSP 입력 폼에서 부서정보를 option으로 만들었다 -> deptList 필요
		List<DeptDTO> deptList = deptService.findAllDept();
		model.addAttribute("deptList",deptList);
		return "emp/empInForm";
	}
	
	// emp 저장 + 이미지 업로드
	@PostMapping("/empSave")
	public String empSave(EmpDTO empDTO) {
		log.info("empSave Start with empDTO->"+empDTO);
		
		// 생각 1. empDTO.getFile()을 리스트화 하거나 향상형 for문을 써서 넣어야 한다
		//    이유_ empDTO.getFile()는 결국 List<Multi..> 이니까
		List<MultipartFile> file = empDTO.getFile();
		List<String> uploadFileNames = utilFile.save(file);
		empDTO.setUploadFileNames(uploadFileNames);
		log.info(uploadFileNames);
		
		int emp_no =  empService.empSave(empDTO);
		
		return "redirect:/emp/list";
	}
	
	@GetMapping("/list")
	public String mainPage(EmpDTO empDTO, Model model) {
		System.out.println("EmpController list Start");
		
		int totalCount = empService.totalEmp();
		Paging page = new Paging(totalCount, empDTO.getCurrentPage());
		empDTO.setStart(page.getStart());
		empDTO.setEnd(page.getEnd());
		
		List<EmpDTO> empList = empService.empList(empDTO);
		model.addAttribute("empList",empList);
		model.addAttribute("page",page);
		return "emp/list";
	}
	
	// 상세보기
	@GetMapping("/detail")
	public String detail(EmpDTO empDTO, Model model) {
		log.info("datail start with emp_no:"+empDTO.getEmp_no());
		EmpDTO empDTO1 = empService.detail(empDTO.getEmp_no());
		log.info("empDTO1: "+empDTO1);
		model.addAttribute("emp",empDTO1);
		return "emp/detail";
	}
	
	// 상세보기 > 수정하기 화면 보여주기
	@GetMapping("/update")
	public String update(EmpDTO empDTO, Model model) {
		log.info("update start with emp_no:"+empDTO.getEmp_no());
		EmpDTO empDTO1 = empService.detail(empDTO.getEmp_no());
		List<DeptDTO> deptList = deptService.findAllDept();
		model.addAttribute("deptList",deptList);
		log.info("empDTO1: "+empDTO1);
		model.addAttribute("emp",empDTO1);
		return "emp/update";
	}
	
	// 삭제
	@PostMapping("/delete")
	public String delete(EmpDTO empDTO) {
		log.info("delete empDTO.EMP_NO"+empDTO.getEmp_no());
		
		List<String> oldFileNames = empService.detail(empDTO.getEmp_no()).getUploadFileNames();
		log.info("delete oldFileNames"+oldFileNames);
		
		// 1. DB 날리기( upload )
		empService.delete(empDTO.getEmp_no());
		// 2. file Server 데이터 삭제
		utilFile.deleteFiles(oldFileNames);
		
		return "redirect:/emp/list";
	}
	
	// 수정(업데이트)
	@PostMapping(value = "/modify")
	public String modify(EmpDTO empDTO) {
		// file에 새로 추가된 이미지 ,uploadFileNames에 개별 리스트 삭제된 이미지 리스트
		System.out.println("EmpDTO empDTO = "+empDTO);
		List<String> oldfileNames = empService.detail(empDTO.getEmp_no()).getUploadFileNames();
		System.out.println("oldfileNames = "+oldfileNames);
		
		// 새로 업로드 해야 하는 파일
		List<MultipartFile> files = empDTO.getFile();
		log.info("files" +files);
		
		// 새로 업로드 되어 만들어진 파일 이름들
		List<String> currentUploadFileNames = utilFile.save(files);
		log.info("currentUploadFileNames" +currentUploadFileNames);
		
		// 화면에서 변화 없이 계속 유지된 파일들
		List<String> uploadFileNames = empDTO.getUploadFileNames();
		log.info("uploadFileNames" +uploadFileNames);
		
		if(currentUploadFileNames != null && currentUploadFileNames.size() > 0) {
			uploadFileNames.addAll(currentUploadFileNames);
			// .addAll:  currentUploadFileNames에 있는 모든 요소를 추가
		}
		log.info("modify empDTO" +empDTO);
		
		// 1. 데이터베이스 수정
		empService.modify(empDTO);
		// 2. 기존이미지 삭제
		if( oldfileNames != null && oldfileNames.size() >0) {
			List<String> removeFile = oldfileNames.stream()
												  .filter(fileName -> uploadFileNames.indexOf(fileName) == -1)
												  .collect(Collectors.toList());
			log.info("removeFile" +removeFile);
			
			utilFile.deleteFiles(removeFile);
		}
		
		return "redirect:/emp/list";
	}
	

}
