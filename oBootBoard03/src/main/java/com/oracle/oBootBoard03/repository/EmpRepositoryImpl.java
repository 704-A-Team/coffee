package com.oracle.oBootBoard03.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard03.domain.Dept;
import com.oracle.oBootBoard03.domain.Emp;
import com.oracle.oBootBoard03.domain.EmpImage;
import com.oracle.oBootBoard03.dto.EmpDTO;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2
@RequiredArgsConstructor
public class EmpRepositoryImpl implements EmpRepository {
	
	private final EntityManager em;

	@Override
	public List<Emp> findAllEmp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Emp empSave(Emp emp) {
		log.info("empSave Start");
		em.persist(emp);
		return emp;
	}

	@Override
	public int totalEmp() {
		log.info("totalEmp Start");
		//                               DB 컬럼명이 아니라 "Java 클래스의 필드명"을 기준으로 작성
		Long total = em.createQuery("select Count(e) From Emp e Where e.del_status = false",Long.class).getSingleResult();
		
		return total.intValue();
	}

	@Override
	public List<EmpDTO> empList1(EmpDTO empDTO) {
		log.info("empList1 start");
		
		String nativeSql = "SELECT emp_no, emp_id, emp_password, emp_name, email, emp_tel, sal, del_status, dept_code, in_date, simage"
				+ "        FROM ("
				+ "		    select rownum rn, a.* FROM ("
				+ "		        select e.*, img.filename simage FROM"
				+ "		                (select * from emp where del_status = 0) e ,"
				+ "		                (select * from emp_image_list where ORDER_NUM = 0) img"
				+ "		            where e.emp_no = img.emp_emp_no(+)"
			//
			//         @ElementCollection    
			//         private List<EmpImage> imageList = new ArrayList<>();
			//         SELECT에서 imageList를 명시할 필요가 없다:  엘리먼트컬렉션 어노테이션은 조인 없이도 값을 가져올 수 있다
				+ "		            order by emp_no"
				+ "		            ) a"
				+ "		    ) WHERE rn BETWEEN  :start and :end";
		
		// Object[] 타입: 쿼리 결과의 한 행(row) 을 배열로 받는 타입
		List<Object[]> empNative = em.createNativeQuery(nativeSql)
								   .setParameter("start", empDTO.getStart())
								   .setParameter("end", empDTO.getEnd())
								   .getResultList()
								   ;
		
		List<EmpDTO> empList = empNative.stream()
									 	.map(EmpDTO::new)
									 	.collect(Collectors.toList())
									 	;
		
		System.out.println("empList->"+empList);
		
		return empList;
	}

	@Override
	public EmpDTO detail(int emp_no) {
		log.info("detail start");
		Emp emp = em.find(Emp.class, emp_no);
		Dept dept = em.find(Dept.class, emp.getDept_code());
			
		log.info("emp->"+emp);
		
		EmpDTO empDTO = EntityToDto(emp, dept);
		
		
		return empDTO; 
	}
	
	private EmpDTO EntityToDto(Emp emp, Dept dept) {
		EmpDTO empDTO = EmpDTO.builder()
							  .emp_no(emp.getEmp_no())
							  .emp_id(emp.getEmp_id())
							  .emp_password(emp.getEmp_password())
							  .emp_name(emp.getEmp_name())
							  .email(emp.getEmail())
							  .emp_tel(emp.getEmp_tel())
							  .sal(emp.getSal())
							  .del_status(emp.isDel_status())
							  .dept_code(emp.getDept_code())
							  .dept_name(dept.getDept_name())
							  .in_date(emp.getIn_date())
							  .build()
							  ;
		// 이미지
		if(emp.getImageList() == null || emp.getImageList().size() == 0) {
			return empDTO;
		}
		
		List<EmpImage> imageList = emp.getImageList();
		List<String> uploadFileNames = imageList.stream()
												.map(empImage->empImage.getFilename())
												.toList()
												;
		empDTO.setUploadFileNames(uploadFileNames);
		
		return empDTO;
	}

	@Override
	public void delete(int emp_no) {
		// 업데이트
		Emp emp = em.find(Emp.class, emp_no);
		emp.changeDel_status(true);	// 이 변경은 DB에 반영됨!
	}
	
	// .ofNullable -> null일 수도 있는 값을 안전하게 감싸주는 역할
	//  Optional.ofNullable(emp)

	
	
}
