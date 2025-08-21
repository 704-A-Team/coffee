package com.oracle.coffee.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.dto.DeptDto;
import com.oracle.coffee.domain.Dept;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptRepositoryImpl implements DeptRepository {

	private final EntityManager em;
	
	
	//부서목록 가져오기
	@Override
	public List<DeptDto> findAllDept() {
		List<Dept> deptEntityList = em.createQuery("select d from Dept d",Dept.class)
				  .getResultList();

			List<DeptDto> deptDtoList = deptEntityList.stream()
					.map(dept->new DeptDto(dept))
						.collect(Collectors.toList());

			return deptDtoList;
	}
		
	//삭제되지않은 부서 개수 조회
	@Override
	public Long deptTotalcount() {
		TypedQuery<Long> query = 	
				em.createQuery("select count(d) from Dept d where d.dept_isdel = 0", Long.class); 
		Long totalCountLong = query.getSingleResult();

		return totalCountLong;
	}

	
	//페이징 된 부서 리스트
	@Override
	public List<DeptDto> findPageDept(DeptDto deptDto) {
		String nativeSql = 
			    "SELECT dept_code, dept_name, dept_tel, dept_isdel, dept_reg_date, dept_emp_name " +
			    "FROM ( " +
			    "    SELECT ROWNUM rn, a.* " +
			    "    FROM ( " +
			    "        SELECT d.dept_code, d.dept_name, d.dept_tel, d.dept_isdel, d.dept_reg_date, e.emp_name AS dept_emp_name " +
			    "        FROM dept d " +
			    "        LEFT JOIN emp e ON d.dept_code = e.emp_dept_code AND e.emp_grade = 2 " +
			    "        WHERE d.dept_isdel = 0 " +
			    "        ORDER BY d.dept_code " +
			    "    ) a " +
			    ") " +
			    "WHERE rn BETWEEN :start AND :end";
			 Query query = em.createNativeQuery(nativeSql); 
			 query.setParameter("start", deptDto.getStart());
			 query.setParameter("end", deptDto.getEnd());
			 
			 List<Object[]> resultList = query.getResultList();

			 return resultList.stream().map(row -> {
			     DeptDto dto = new DeptDto();
			     dto.setDept_code(((Number) row[0]).intValue());
			     dto.setDept_name((String) row[1]);             
			     dto.setDept_tel((String) row[2]);              
			     dto.setDept_isdel(((Number) row[3]).intValue()); 
			     dto.setDept_reg_date(((java.sql.Timestamp) row[4]).toLocalDateTime());
			     dto.setDept_emp_name((String) row[5]);
			     
			     return dto;
			 }).collect(Collectors.toList());
	}

	//부서 저장
	@Override
	public Dept deptSave(Dept dept) {
		em.persist(dept);
		return dept;
	}

	//부서코드로 검색 
	@Override
	public DeptDto findByDept_code(int dept_code) {
		Dept dept = em.find(Dept.class, dept_code);
		
		return new DeptDto(dept);
	}

	//부서 업데이트 
	@Override
	public DeptDto updateDept(DeptDto deptDto) {
		 String updateSql =
			        "UPDATE dept SET " +
			        "  dept_name = :name, " +
			        "  dept_tel  = :tel, " +
			        "  dept_isdel = :del " +
			        "WHERE dept_code = :code";

			    em.createNativeQuery(updateSql)
			      .setParameter("name", deptDto.getDept_name())
			      .setParameter("tel", deptDto.getDept_tel())
			      .setParameter("del", deptDto.getDept_isdel())
			      .setParameter("code", deptDto.getDept_code())
			      .executeUpdate();
		
		return findByDept_code(deptDto.getDept_code());
	}
	
	//부서 삭제
	@Override
	public void deptDelete(int dept_code) {
		Dept dept = em.find(Dept.class, dept_code);
		dept.changeDept_isdel(1);
		
	}




}