package com.oracle.coffee.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import com.oracle.coffee.domain.Emp;
import com.oracle.coffee.dto.EmpDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpRepositoryImpl implements EmpRepository {
	
	private final EntityManager em;

	@Override
	public Long empTotalcount() {
		TypedQuery<Long> query = 	
				em.createQuery("select count(e) from Emp e where e.emp_isdel = false", Long.class); 
		Long totalCountLong = query.getSingleResult();

		return totalCountLong;
	}

	@Override
	public List<EmpDto> findPageEmp(EmpDto empDto) {
		String nativeSql = 
			    "SELECT * FROM ( " +
			    "    SELECT ROWNUM rn, e.* FROM ( " +
			    "        SELECT emp.emp_code, emp.emp_name, emp.emp_tel, emp.emp_dept_code, emp.emp_grade, " +
			    "               emp.emp_sal, emp.emp_email, emp.emp_isdel, emp.emp_register, emp.emp_reg_date, " +
			    "               d.dept_name " +
			    "        FROM emp " +
			    "        JOIN dept d ON emp.emp_dept_code = d.dept_code " +
			    "        WHERE emp.emp_isdel = 0 " +
			    "        ORDER BY emp.emp_code " +
			    "    ) e " +
			    ") " +
			    "WHERE rn BETWEEN :start AND :end";


		Query query = em.createNativeQuery(nativeSql); // ← 여기만 바꿈
		query.setParameter("start", empDto.getStart());
		query.setParameter("end", empDto.getEnd());

		List<Object[]> resultList = query.getResultList();

		List<EmpDto> empDtoList = resultList.stream().map(row -> {
		    EmpDto dto = new EmpDto();
		    dto.setEmp_code(((Number) row[1]).intValue());
		    dto.setEmp_name((String) row[2]);
		    dto.setEmp_tel((String) row[3]);
		    dto.setEmp_dept_code(((Number) row[4]).intValue());
		    dto.setEmp_grade(((Number) row[5]).intValue());
		    dto.setEmp_sal(((Number) row[6]).intValue());
		    dto.setEmp_email((String) row[7]);
		    dto.setEmp_isDel(((Number) row[8]).intValue() == 1);
		    dto.setEmp_register(((Number) row[9]).intValue());
		    dto.setEmp_reg_date(((java.sql.Timestamp) row[10]).toLocalDateTime());
		    dto.setDept_code((String) row[11]); // join된 부서명
		    return dto;
		}).collect(Collectors.toList());

		return empDtoList;

		}


	@Override
	public List<EmpDto> findAllEmp() {
		List<Emp> empEntityList = em.createQuery("select e from Emp e",Emp.class)
				  .getResultList();

			List<EmpDto> empDtoList = empEntityList.stream()
					.map(emp->new EmpDto(emp))
						.collect(Collectors.toList());

			return empDtoList;
	}

	@Override
	public Emp empSave(Emp emp) {
		em.persist(emp);
		return emp;
	}

	@Override
	public Emp findByEmp_code(int emp_code) {
			Emp emp = em.find(Emp.class, emp_code);
			return emp;
	}

	@Override
	public Optional<Emp> findByEmp_codeUpdate(int emp_code) {
		Emp foundEmp = em.find(Emp.class, emp_code);
		
		Optional<Emp> updatedEmp = Optional.ofNullable(foundEmp);
		return updatedEmp;
	}

	@Override
	public void empDelete(int emp_code) {
		Emp emp = em.find(Emp.class, emp_code);
		emp.changeEmp_isdel(true);
	}

}
