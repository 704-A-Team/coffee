package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.Emp;
import com.oracle.oBootMybatis01.service.EmpService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AjaxController {
	
	private final EmpService es;
	
	// ajaxForm Test 입력화면
	@RequestMapping("/ajaxForm")
	public String ajaxForm() {
		System.out.println("ajaxForm Start");
		return "ajaxForm";
	}
	
	@ResponseBody
	@RequestMapping("getDeptName")
	public String getDeptName(Dept dept, Model model) {
		System.out.println("AjaxController deptno->" + dept.getDeptno());
		String deptName   = es.deptName(dept.getDeptno());
		// mapper --> EmpDept(tkDeptName)
		System.out.println("AjaxController deptName->"+deptName);
		return deptName;
	}
	
	// Ajax List Test
	@RequestMapping("/listEmpAjaxForm")
	public String listEmpAjaxForm(Model model) {
		Emp emp = new Emp();
		System.out.println("Ajax List Test Start");
		// parameter emp -> Page만 추가 Setting
		emp.setStart(1);	// 시작시 1
		emp.setEnd(10);		// 시작시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmpAjax listEmp.size()->" + listEmp.size());
		model.addAttribute("result", "kkk");
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm";
	}
	
	@ResponseBody
	@RequestMapping("/empSerializeWrite")
	public Map<String, Object> empSerializeWrite(@RequestBody @Valid Emp emp)  {
		System.out.println("EmpController empSerializeWrite Start");
		System.out.println("EmpController empSerializeWrite emp->" + emp);
		
		// int wirteResult = kkk.writeEmp(emp);
		// String followingProStr = Integer.toString(followingPro);
		int writeResult = 1;
		
		Map<String, Object> resultMap = new HashMap<>();
		System.out.println("EmpController empSerializeWrite wirteResult-> " + writeResult);
		// DTO -> 여러번 사용할 때 전달 / MAP -> 한번만 쓸 때 전달
		resultMap.put("writeResult", writeResult);
		resultMap.put("anyResult", "anyR");
		return resultMap;
	}
	
	@RequestMapping(value = "listEmpAjaxForm2")
    public String listEmpAjaxForm2(Model model) {
		System.out.println("listEmpAjaxForm2 Start..");
		Emp emp = new Emp();
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(1);   // 시작시 1
		emp.setEnd(15);    // 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("listEmpAjaxForm2 listEmp.size()->"+listEmp.size());
		model.addAttribute("listEmp",listEmp);
	    return "listEmpAjaxForm2";
    }
	
	// 결과 --> String
		@ResponseBody
		@RequestMapping("/empnoDelete")
		public String empnoDelete(Emp emp)  {
			System.out.println("empnoDelete03 Start");
			System.out.println("empnoDelete03 emp->"+emp);
			int delStatus = es.deleteEmp(emp.getEmpno());
			String delStatusStr = Integer.toString(delStatus);
			return delStatusStr;
		}
	
	// 결과 --> 객체
	@ResponseBody
	@RequestMapping("/empnoDelete03")
	public Map<String, Object> empnoDelete03(Emp emp)  {
		System.out.println("empnoDelete03 Start");
		System.out.println("empnoDelete03 emp->"+emp);
		int delStatus = es.deleteEmp(emp.getEmpno());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("delStatus", delStatus);
		return resultMap;
	}
	
	@RequestMapping("/listEmpAjaxForm3")
	public String listEmpAjaxForm3(Model model) {
		System.out.println("listEmpAjaxForm3 start");
		Emp emp = new Emp();
		emp.setStart(1);
		emp.setEnd(15);
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println(""+listEmp.size());
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm3";
		
	}
	
	@ResponseBody
	@RequestMapping("/empListUpdate")
	public Map<String, Object> empListUpdate(@RequestBody @Valid List<Emp> listEmp)  {
		System.out.println("EmpController empListUpdate Start");
		int updateResult = 1;
		
		for(Emp emp : listEmp)  {
			System.out.println("emp->" + emp);
			// int writeRsult = kkk.listUpdateEmp(emp);
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("updateResult", updateResult);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "transactionInsertUpdate")
	public String transactionInsertUpdate(Emp emp, Model model)  {
		System.out.println("transactionInsertUpdate Start");
		// member Insert 성공과 실패
		int returnMember = es.transactionInsertUpdate();
		System.out.println("transactionInsertUpdate returnMember-> "+returnMember);
		String returnMemberString = String.valueOf(returnMember);
		
		return returnMemberString;
	}
}
