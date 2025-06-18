package com.oracle.oBootMybatis01.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

// Join 목적
@Data
public class EmpDept {
	// Emp 용
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;
	private int sal;
	private int comm;
	private int deptno; 
	
	// Dept 용 (data가 많다는 가정, 데이터 적으면 EMP에 한다)
	private String dname;
	private String loc;
}
