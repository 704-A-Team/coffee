package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;

import com.oracle.oBootMybatis01.dto.Dept;
import com.oracle.oBootMybatis01.dto.DeptVO;

public interface DeptDao {
List<Dept>	deptSelect();
void 		insertDept(DeptVO deptVO);
void 		selListDept(HashMap<String, Object> map);
String 		deptName(int deptno);
}
