package com.oracle.oBootMybatis01.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dept {
	private int deptno;
	private String dname;
	private String loc;
}
