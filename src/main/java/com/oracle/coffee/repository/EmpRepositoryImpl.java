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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpRepositoryImpl implements EmpRepository {
	
	private final EntityManager em;
    private final PasswordEncoder passwordEncoder;

    // ===== 유틸 =====
    private static Integer toInt(Object v){ return v==null? null : ((Number)v).intValue(); }
    private static String  toStr(Object v){ return v==null? null : v.toString(); }
    private static java.time.LocalDateTime toLdt(Object v){
        if(v==null) return null;
        if(v instanceof java.sql.Timestamp ts) return ts.toLocalDateTime();
        if(v instanceof java.time.LocalDateTime ldt) return ldt;
        return null;
    }
    private static java.sql.Date toSqlDate(Object v){
        if(v==null) return null;
        if(v instanceof java.sql.Date d) return d;
        if(v instanceof java.util.Date d) return new java.sql.Date(d.getTime());
        return null;
    }
    private static boolean notBlank(String s){ return s!=null && !s.isBlank(); }

    @Override
    public Long empTotalcount(EmpDto empDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(*) ");
        sb.append("  FROM emp emp ");
        sb.append("  JOIN dept d ON emp.emp_dept_code = d.dept_code ");
        sb.append("  LEFT JOIN bunryu b ON emp.emp_grade = b.mcd AND b.bcd = 900 AND b.mcd BETWEEN 0 AND 5 ");
        sb.append(" WHERE emp.emp_isdel = 0 ");

        if (notBlank(empDto.getSearchKeyword())) {
            if ("empCode".equalsIgnoreCase(empDto.getSearchType())) {
                sb.append(" AND TO_CHAR(emp.emp_code) LIKE :kw ");
            } else {
                sb.append(" AND emp.emp_name LIKE :kw ");
            }
        }
        // 부서명 필터
        if (notBlank(empDto.getDeptName()) && !"전체".equals(empDto.getDeptName())) {
            sb.append(" AND d.dept_name = :deptName ");
        }
        // 직급명 필터
        if (notBlank(empDto.getGradeName()) && !"전체".equals(empDto.getGradeName())) {
            sb.append(" AND b.cd_contents = :gradeName ");
        }

        Query q = em.createNativeQuery(sb.toString());

        if (notBlank(empDto.getSearchKeyword())) {
            q.setParameter("kw", "%" + empDto.getSearchKeyword().trim() + "%");
        }
        if (notBlank(empDto.getDeptName()) && !"전체".equals(empDto.getDeptName())) {
            q.setParameter("deptName", empDto.getDeptName().trim());
        }
        if (notBlank(empDto.getGradeName()) && !"전체".equals(empDto.getGradeName())) {
            q.setParameter("gradeName", empDto.getGradeName().trim());
        }

        Number n = (Number) q.getSingleResult();
        return n.longValue();
    }

    @Override
    public List<EmpDto> findPageEmp(EmpDto empDto) {
        String selectCols =
            "emp.emp_code, emp.emp_name, emp.emp_tel, " +
            "emp.emp_dept_code, emp.emp_grade, emp.emp_sal, emp.emp_email, " +
            "emp.emp_isdel, emp.emp_register, emp.emp_reg_date, emp.emp_ipsa_date, emp.emp_birth, " +
            "d.dept_name, b.cd_contents";

        StringBuilder where = new StringBuilder(" WHERE emp.emp_isdel = 0 ");

        if (notBlank(empDto.getSearchKeyword())) {
            if ("empCode".equalsIgnoreCase(empDto.getSearchType())) {
                where.append(" AND TO_CHAR(emp.emp_code) LIKE :kw ");
            } else {
                where.append(" AND emp.emp_name LIKE :kw ");
            }
        }
        // 부서명 필터
        if (notBlank(empDto.getDeptName()) && !"전체".equals(empDto.getDeptName())) {
            where.append(" AND d.dept_name = :deptName ");
        }
        // 직급명 필터
        if (notBlank(empDto.getGradeName()) && !"전체".equals(empDto.getGradeName())) {
            where.append(" AND b.cd_contents = :gradeName ");
        }

        String base =
            "SELECT " + selectCols +
            "  FROM emp emp " +
            "  JOIN dept d ON emp.emp_dept_code = d.dept_code " +
            "  LEFT JOIN bunryu b ON emp.emp_grade = b.mcd AND b.bcd = 900 AND b.mcd BETWEEN 0 AND 5 " +
                 where +
            " ORDER BY emp.emp_code ASC "; 

        String sql =
            "SELECT * FROM ( " +
            "  SELECT t.*, ROWNUM rn FROM ( " + base + " ) t " +
            ") WHERE rn BETWEEN :start AND :end";

        Query q = em.createNativeQuery(sql);

        if (notBlank(empDto.getSearchKeyword())) {
            q.setParameter("kw", "%" + empDto.getSearchKeyword().trim() + "%");
        }
        if (notBlank(empDto.getDeptName()) && !"전체".equals(empDto.getDeptName())) {
            q.setParameter("deptName", empDto.getDeptName().trim());
        }
        if (notBlank(empDto.getGradeName()) && !"전체".equals(empDto.getGradeName())) {
            q.setParameter("gradeName", empDto.getGradeName().trim());
        }

        q.setParameter("start", empDto.getStart());
        q.setParameter("end",   empDto.getEnd());

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        return rows.stream().map(row -> {
            EmpDto dto = new EmpDto();
            int i = 0;
            dto.setEmp_code(toInt(row[i++]));           
            dto.setEmp_name(toStr(row[i++]));           
            dto.setEmp_tel(toStr(row[i++]));            
            dto.setEmp_dept_code(toInt(row[i++]));      
            dto.setEmp_grade(toInt(row[i++]));          
            dto.setEmp_sal(toInt(row[i++]));            
            dto.setEmp_email(toStr(row[i++]));          
            dto.setEmp_isdel(toInt(row[i++]));          
            dto.setEmp_register(toInt(row[i++]));       
            dto.setEmp_reg_date(toLdt(row[i++]));       
            dto.setEmp_ipsa_date(toSqlDate(row[i++]));  
            dto.setEmp_birth(toSqlDate(row[i++]));      
            dto.setDept_code(toStr(row[i++]));          
            dto.setEmp_grade_detail(toStr(row[i++]));   
            return dto;
        }).collect(Collectors.toList());
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
	    dto.setEmp_ipsa_date(row[10] != null ? new java.sql.Date(((java.util.Date) row[10]).getTime()) : null);
	    dto.setEmp_birth(row[11] != null ? new java.sql.Date(((java.util.Date) row[11]).getTime()) : null);
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
	
	
	@Transactional
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
	        "  emp_ipsa_date = :ipsa_date, " +
	        "  emp_birth = :birth " +
	        "WHERE emp_code = :code";
	    
	    em.createNativeQuery(updateSql)
	      .setParameter("name", empDto.getEmp_name())
	      .setParameter("tel", empDto.getEmp_tel())
	      .setParameter("dept", empDto.getEmp_dept_code())
	      .setParameter("grade", empDto.getEmp_grade())
	      .setParameter("sal", empDto.getEmp_sal())
	      .setParameter("email", empDto.getEmp_email())
	      .setParameter("del", empDto.getEmp_isdel())
	      .setParameter("ipsa_date", empDto.getEmp_ipsa_date())
	      .setParameter("birth", empDto.getEmp_birth())
	      .setParameter("code", empDto.getEmp_code())
	      .executeUpdate();
	    
	    
	    //업뎃 후 ROLE 변경. 부장이상일경우 role_manager 아니면 user 
	    String targetRole = null;
	    int grade = empDto.getEmp_grade();
	    
	    if (grade >= 2) {
	    	targetRole = "ROLE_MANAGER";
	    } else targetRole = "ROLE_USER";
	    
	    //roles 업데이트
	    if (targetRole != null) {
	    	final String ROLE_TABLE = "ACCOUNT";
	    	em.createNativeQuery(
		            "UPDATE " + ROLE_TABLE + " SET roles = :roles WHERE emp_code = :empCode"
	    	)
	    	.setParameter("roles", targetRole)
	    	.setParameter("empCode",empDto.getEmp_code())
	    	.executeUpdate();
	    }

	    return findByEmp_code(empDto.getEmp_code());
	}


}