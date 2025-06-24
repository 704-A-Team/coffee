package com.oracle.oBootMybatis01.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Member1 {
	private String id;
	private String name;
	private String password;
	private Date   reg_date;
}