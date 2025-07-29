package com.oracle.coffee.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

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
	
	
	@Override
	public List<DeptDto> findAllDept() {
		List<Dept> deptEntityList = em.createQuery("select d from Dept d",Dept.class)
				  .getResultList();

			List<DeptDto> deptDtoList = deptEntityList.stream()
					.map(dept->new DeptDto(dept))
						.collect(Collectors.toList());

			return deptDtoList;
	}
		
	@Override
	public Long deptTotalcount() {
		TypedQuery<Long> query = 
				em.createQuery("select count(d) from Dept d where d.dept_isDel = false", Long.class); 
		Long totalCountLong = query.getSingleResult();

		return totalCountLong;
	}

	
	@Override
	public List<DeptDto> findPageDept(DeptDto deptDto) {
		  String nativeSql = "SELECT dept_code, dept_name, dept_tel,dept_isdel, dept_reg_date " 
                  + "FROM ( "
                  + "    SELECT ROWNUM rn, a.* "
                  + "    FROM ( "
                  + "        SELECT dept_code, dept_name, dept_tel,dept_isdel, dept_reg_date " 
                  + "        FROM   DEPT " 
                  + "        WHERE  dept_isdel = 0"
                  + "        ORDER BY dept_code "
                  + "    ) a "
                  + ") "
                  + "WHERE rn BETWEEN :start AND :end";

 Query query = em.createNativeQuery(nativeSql, Dept.class); 
 query.setParameter("start", deptDto.getStart());
 query.setParameter("end", deptDto.getEnd());
 
 List<Dept> deptEntityList = query.getResultList();
 System.out.println("DeptRepositoryImplfindPageDept deptEntityList->"+deptEntityList);

 List<DeptDto> deptDtoList = deptEntityList.stream()
							                .map(dept->new DeptDto(dept))
 		                               .collect(Collectors.toList());
 System.out.println("DeptRepositoryImplfindPageDept deptDtoList->"+deptDtoList);
 return deptDtoList;	
	}



}