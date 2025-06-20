package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@Controller + @ResponseBody
// string, json 탄다

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
public class EmpRestController {
	
	private final EmpService es;
	
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("EmpRestController helloText Start");
		String hello = "안녕";
		//      StringConverter
		return hello;
	}
	
	// http://jsonviewer.stack.hu/
	// postman
	@RequestMapping("/sample/sendVO2")
	public SampleVO sendV02(Dept dept) {
		System.out.println("@RestController dept.getDeptno()->"+dept.getDeptno());
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(dept.getDeptno());
		//      JsonConverter
		return vo;
	}
	
	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3() {
		System.out.println("@RestController sendVO3 Start");
		List<Dept> deptList = es.deptSelect();
		return deptList;
	}
}
