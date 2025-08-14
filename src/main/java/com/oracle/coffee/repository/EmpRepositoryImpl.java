package com.oracle.coffee.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.domain.Account;
import com.oracle.coffee.domain.Emp;
import com.oracle.coffee.dto.EmpDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpRepositoryImpl implements EmpRepository {
	
	private final EntityManager em;
    private final PasswordEncoder passwordEncoder;

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
			    "               emp.emp_sal, emp.emp_email, emp.emp_isdel, emp.emp_register, emp.emp_reg_date, emp.emp_ipsa_date, emp_birth," +
			    "               d.dept_name, b.cd_contents" +
			    "        FROM emp " +
			    "        JOIN dept d ON emp.emp_dept_code = d.dept_code " +
			    "        LEFT JOIN bunryu b ON emp.emp_grade = b.mcd AND b.bcd = 900 AND b.mcd BETWEEN 0 AND 5 " +
			    "        WHERE emp.emp_isdel = 0 " +
			    "        ORDER BY emp.emp_code " +
			    "    ) e " +
			    ") " +
			    "WHERE rn BETWEEN :start AND :end";


		Query query = em.createNativeQuery(nativeSql);
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
		    dto.setEmp_isdel(((Number) row[8]).intValue());
		    dto.setEmp_register(((Number) row[9]).intValue());
		    dto.setEmp_reg_date(((java.sql.Timestamp) row[10]).toLocalDateTime());
		    dto.setEmp_ipsa_date(new java.sql.Date(((java.sql.Timestamp) row[11]).getTime()));
		    dto.setEmp_birth(new java.sql.Date(((java.sql.Timestamp) row[12]).getTime()));
		    dto.setDept_code((String) row[13]); // join된 부서명
		    dto.setEmp_grade_detail((String) row[14]); //join된 직급
		    
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

    public Emp empSave(Emp emp) {
        // 1) emp 저장
        if (emp.getEmp_isdel() == 1) emp.changeEmp_isdel(0);
        em.persist(emp);
        em.flush(); 

        // 2) emp_tel 뒤 4자리 → 해시
        String tel = emp.getEmp_tel();
        if (tel == null || tel.isBlank()) {
            throw new IllegalArgumentException("전화번호가 없습니다.");
        }	
        String last4 = extractLast4Digits(tel);
        if (last4.isEmpty()) {
            throw new IllegalArgumentException("전화번호에서 숫자 4자리를 추출할 수 없습니다.");
        }
        String encoded = passwordEncoder.encode(last4);

        // 3) Account 생성 (id는 시퀀스로 자동)
        Account account = new Account();
        account.setEmp_code(emp.getEmp_code());              // EMP_CODE 저장
        account.setUsername(String.valueOf(emp.getEmp_code())); // USERNAME = emp_code
        account.setPassword(encoded);                       // PASSWORD = hash(last4)
        if (emp.getEmp_grade() >= 2) { 
            account.setRoles("ROLE_MANAGER");
        } else {
            account.setRoles("ROLE_USER");
        }                  

        em.persist(account); 

        return emp;
    }

    private String extractLast4Digits(String tel) {
        String digits = tel.replaceAll("\\D", "");
        int len = digits.length();
        if (len < 4) return "";
        return digits.substring(len - 4);
    }
    
	@Override
	public EmpDto findByEmp_code(int emp_code) {
	    String sql =
	        "SELECT emp.emp_code, emp.emp_name, emp.emp_tel, emp.emp_dept_code, emp.emp_grade, " +
	        "       emp.emp_sal, emp.emp_email, emp.emp_isdel, emp.emp_register, emp.emp_reg_date, emp.emp_ipsa_date, emp.emp_birth, " +
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
	    dto.setEmp_isdel(((Number) row[7]).intValue());
	    dto.setEmp_register(((Number) row[8]).intValue());
	    dto.setEmp_reg_date(((java.sql.Timestamp) row[9]).toLocalDateTime());
	    dto.setEmp_ipsa_date(new java.sql.Date(((java.sql.Timestamp) row[10]).getTime()));
	    dto.setEmp_birth(new java.sql.Date(((java.sql.Timestamp) row[11]).getTime()));
	    dto.setDept_code((String) row[12]); // 부서명
	    dto.setEmp_grade_detail((String) row[13]); // 직급	    

	    return dto;
	}
	
	@Transactional
	@Override
	public void empDelete(int emp_code) {
		Emp emp = em.find(Emp.class, emp_code);
		emp.changeEmp_isdel(1);
		 
		//퇴사한 사원 guest처리 
		em.createNativeQuery(
			        "UPDATE account SET roles = :role WHERE emp_code = :empCode"
			    )
			    .setParameter("role", "ROLE_GUEST")
			    .setParameter("empCode", emp_code)
			    .executeUpdate();
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
	        "  emp_birth = :birth" +
	        "WHERE emp_code = :code";
	    
	    em.createNativeQuery(updateSql)
	      .setParameter("name", empDto.getEmp_name())
	      .setParameter("tel", empDto.getEmp_tel())
	      .setParameter("dept", empDto.getEmp_dept_code())
	      .setParameter("grade", empDto.getEmp_grade())
	      .setParameter("sal", empDto.getEmp_sal())
	      .setParameter("email", empDto.getEmp_email())
	      .setParameter("del", empDto.getEmp_isdel())
	      .setParameter("code", empDto.getEmp_code())
	      .setParameter("ipsa_date", empDto.getEmp_ipsa_date())
	      .setParameter("birth", empDto.getEmp_birth())
	      .executeUpdate();

	    return findByEmp_code(empDto.getEmp_code());
	}


}