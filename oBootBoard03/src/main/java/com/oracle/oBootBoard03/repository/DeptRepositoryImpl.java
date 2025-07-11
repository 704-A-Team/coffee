package com.oracle.oBootBoard03.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard03.domain.Dept;
import com.oracle.oBootBoard03.dto.DeptDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2
@RequiredArgsConstructor
public class DeptRepositoryImpl implements DeptRepository {
	
	private final EntityManager em;
	
	@Override
	public Dept deptSave(Dept dept) {
		System.out.println("DeptRepositoryImpl deptSave Start");
		try {
			em.persist(dept);
			System.out.println("DeptRepositoryImpl deptSave dept: "+dept);
		} catch (Exception e) {
			System.out.println("DeptRepositoryImpl deptSave Exception: "+e.getMessage());
		}
		return dept;
	}
	
	@Override
	public int totalDept() {
		// 非 사용
		System.out.println("DeptRepositoryImpl totalDept Start");
		Long total;
		
		total = em.createQuery("Select Count(d) From Dept d Where dept_gubun=false", Long.class).getSingleResult();
		System.out.println("DeptRepositoryImpl totalDept total-> "+total);
		
		return total.intValue();
	}
	
	@Override
	public List<DeptDTO> findAllDept(DeptDTO deptDTO) {
		// 非 사용
		// JPQL 쿼리
	    // Oracle 11g ROWNUM을 이용한 페이징 네이티브 쿼리
	    // d1_0 대신 dept_code, dept_captain 등 실제 컬럼명 사용 (엔티티 필드명이 아님!)
	    // SELECT 절에 엔티티의 모든 컬럼을 명시하거나, 엔티티 매핑 정보를 활용해야 함
	    // 여기서는 Dept 엔티티의 모든 컬럼을 명시한다고 가정
	    String nativeSql = "SELECT dept_code, dept_captain, dept_gubun, dept_loc, dept_name, dept_tel, in_date " // 실제 컬럼명들을 나열
	                     + "FROM ( "
	                     + "    SELECT ROWNUM rn, a.* "
	                     + "    FROM ( "
	                     + "        SELECT dept_code, dept_captain, dept_gubun, dept_loc, dept_name, dept_tel, in_date " // 실제 컬럼명들을 나열
	                     + "        FROM Dept " // 테이블명은 엔티티명이 아닌 실제 DB 테이블명
	                     + "        ORDER BY dept_code "
	                     + "    ) a "
	                     + ") "
	                     + "WHERE rn BETWEEN :start AND :end";

	    // em.createNativeQuery()를 사용하고, 결과를 Dept.class로 매핑하도록 지정
	    Query query = em.createNativeQuery(nativeSql, Dept.class); // 두 번째 인자로 엔티티 클래스를 주면 자동으로 매핑 시도

	    // :start 파라미터에 값 넣기
	    query.setParameter("start", deptDTO.getStart());
	    // :end 파라미터에 값 넣기
	    query.setParameter("end", deptDTO.getEnd());

	    //         JPA는 무조건 Entity 돌려준다
	    List<Dept> deptEntityList = query.getResultList();
	    System.out.println("DeptRepositoryImplfindPageDept deptEntityList->"+deptEntityList);

        // 2. Stream API를 사용하여 Entity List를 DTO List로 변환
       // List<DeptDTO> deptDtoList = deptEntityList.stream()
								                // 각 dept 엔티티를 deptDto 객체로 매핑 (변환)
								                // deptDto::new는 deptDto(Dept dept) 생성자를 호출하는 것과 같아.
								                //.map(DeptDTO::new)
								                //.map(dept->new DeptDto(dept))
								                // 매핑된 DTO 객체들을 List로 다시 수집
        		                                //.collect(Collectors.toList());
	    // 주석 문에 있는거 쓰려면 DeptDTO에 new 할 수 있도록 생성자 만들어 줘야 하고
	    // yml에 추가
	    /*
	    #Jpa Setting
	    jpa:
	      # database-platform: org.hibernate.dialect.OracleDialect  # Jpa사용시 11g에 대한 방언 설정 --> Paging 작업 
	    */
	    System.out.println("DeptRepositoryImplfindPageDept deptDtoList->");
	    List<DeptDTO> dd =null;
	    // 쿼리 실행 및 결과 반환
	    //return deptDtoList;
	    return dd;
	}

	@Override
	public Dept deptModify(int dept_code) {
		log.info("deptModify Start");
		Dept dept = null;
		try {
			dept = em.find(Dept.class, dept_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}

	@Override
	public int deptUpdate(Dept dept) {
		log.info("deptUpdate Start");
		int result = 0;
		Dept deptUpdate = em.find(Dept.class, dept.getDept_code());
		try {
			if(deptUpdate != null) {
				deptUpdate.changeDept_name(dept.getDept_name());
				deptUpdate.changeDept_captain(dept.getDept_captain());
			//	deptUpdate.changeDept_gubun(dept.isDept_gubun());
				deptUpdate.changeDept_loc(dept.getDept_loc());
				deptUpdate.changeDept_tel(dept.getDept_tel());
				deptUpdate.changeIn_date(dept.getIn_date());
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	@Override
	public int deptDelete(int dept_code) {
		log.info("deptDelete Start");
		int result = 0;
		Dept deptUpdate = em.find(Dept.class, dept_code);
		try {
			deptUpdate.changeDept_gubun(true);
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	@Override
	public List<Dept> findAllDept() {
		log.info("findAllDept Start");
		List<Dept> deptList = em.createQuery("select d From Dept d Where dept_gubun=false").getResultList();
		return deptList;
	}

	
}
