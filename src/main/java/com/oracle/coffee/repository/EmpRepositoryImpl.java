package com.oracle.coffee.repository;

import java.util.List;
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
				em.createQuery("select count(e) from Emp e where e.emp_isdel = 0", Long.class); 
		Long totalCountLong = query.getSingleResult();

		return totalCountLong;
	}

	@Override
	public List<EmpDto> findPageEmp(EmpDto empDto) {
		String nativeSql = 
			    "SELECT * FROM ( " +
			    "    SELECT ROWNUM rn, e.* FROM ( " +
			    "        SELECT emp.emp_code, emp.emp_name, emp.emp_tel, emp.emp_dept_code, emp.emp_grade, " +
			    "               emp.emp_sal, emp.emp_email, emp.emp_isdel, emp.emp_register, emp.emp_reg_date, emp.emp_ipsa_date, " +
			    "               d.dept_name, b.cd_contents" +
			    "        FROM emp " +
			    "        JOIN dept d ON emp.emp_dept_code = d.dept_code " +
			    "        LEFT JOIN bunryu b ON emp.emp_grade = b.mcd AND b.bcd = 900 AND b.mcd BETWEEN 0 AND 5 " +
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
		    dto.setEmp_isDel(((Number) row[8]).intValue());
		    dto.setEmp_register(((Number) row[9]).intValue());
		    dto.setEmp_reg_date(((java.sql.Timestamp) row[10]).toLocalDateTime());
		    dto.setEmp_ipsa_date(new java.sql.Date(((java.sql.Timestamp) row[11]).getTime()));
		    dto.setDept_code((String) row[12]); // join된 부서명
		    dto.setEmp_grade_detail((String) row[13]); //join된 직급
		    
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
	public EmpDto findByEmp_code(int emp_code) {
	    String sql =
	        "SELECT emp.emp_code, emp.emp_name, emp.emp_tel, emp.emp_dept_code, emp.emp_grade, " +
	        "       emp.emp_sal, emp.emp_email, emp.emp_isdel, emp.emp_register, emp.emp_reg_date, emp.emp_ipsa_date, " +
	        "       d.dept_name, b.cd_contents " +
	        "FROM emp " +
	        "JOIN dept d ON emp.emp_dept_code = d.dept_code " +
	        "LEFT JOIN bunryu b ON emp.emp_grade = b.mcd AND b.bcd = 900 AND b.mcd BETWEEN 0 AND 5 " +
	        "WHERE emp.emp_code = :emp_code";

	    Object[] row = (Object[]) em.createNativeQuery(sql)
	                                .setParameter("emp_code", emp_code)
	                                .getSingleResult();

	    EmpDto dto = new EmpDto();
	    dto.setEmp_code(((Number) row[0]).intValue());
	    dto.setEmp_name((String) row[1]);
	    dto.setEmp_tel((String) row[2]);
	    dto.setEmp_dept_code(((Number) row[3]).intValue());
	    dto.setEmp_grade(((Number) row[4]).intValue());
	    dto.setEmp_sal(((Number) row[5]).intValue());
	    dto.setEmp_email((String) row[6]);
	    dto.setEmp_isDel(((Number) row[7]).intValue());
	    dto.setEmp_register(((Number) row[8]).intValue());
	    dto.setEmp_reg_date(((java.sql.Timestamp) row[9]).toLocalDateTime());
	    dto.setEmp_ipsa_date(new java.sql.Date(((java.sql.Timestamp) row[10]).getTime()));
	    dto.setDept_code((String) row[11]); // 부서명
	    dto.setEmp_grade_detail((String) row[12]); // 직급명

	    return dto;
	}
	
	@Override
	public void empDelete(int emp_code) {
		Emp emp = em.find(Emp.class, emp_code);
		emp.changeEmp_isdel(1);
	}

	@Override
	public EmpDto updateEmp(EmpDto empDto) {
	    String updateSql =
	        "UPDATE emp SET " +
	        "  emp_name = :name, " +
	        "  emp_tel  = :tel, " +
	        "  emp_dept_code = :dept, " +
	        "  emp_grade = :grade, " +
	        "  emp_sal = :sal, " +
	        "  emp_email = :email, " +
	        "  emp_isdel = :del, " +
	        "  emp_ipsa_date = :ipsa_date " +
	        "WHERE emp_code = :code";

	    em.createNativeQuery(updateSql)
	      .setParameter("name", empDto.getEmp_name())
	      .setParameter("tel", empDto.getEmp_tel())
	      .setParameter("dept", empDto.getEmp_dept_code())
	      .setParameter("grade", empDto.getEmp_grade())
	      .setParameter("sal", empDto.getEmp_sal())
	      .setParameter("email", empDto.getEmp_email())
	      .setParameter("del", empDto.getEmp_isDel())
	      .setParameter("code", empDto.getEmp_code())
	      .setParameter("ipsa_date", empDto.getEmp_ipsa_date())
	      .executeUpdate();

	    return findByEmp_code(empDto.getEmp_code());
	}


}