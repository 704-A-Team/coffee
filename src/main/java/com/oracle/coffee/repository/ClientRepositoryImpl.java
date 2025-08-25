package com.oracle.coffee.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.domain.Account;
import com.oracle.coffee.domain.Client;
import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.util.SecurityUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

	private final EntityManager em;
	private final PasswordEncoder passwordEncoder;

	//조건에 맞는 거래처 세기 
	@Override
	public Long clientTotalcount(ClientDto cond) {
	    final boolean hasStatus = (cond.getStatus() != null);
	    final boolean hasKw = (cond.getSearchKeyword() != null && !cond.getSearchKeyword().isBlank());
	    final String st = (cond.getSearchType() == null || cond.getSearchType().isBlank())
	            ? "all" : cond.getSearchType();

	    String sql =
	        "SELECT COUNT(*) " +
	        "  FROM client_tb cl " +
	        " WHERE 1=1 " +
	        (hasStatus ? " AND cl.client_status = :status " : "") +
	        (hasKw
	            ? (" AND (" +
	                ("clientName".equals(st)
	                    ? " LOWER(cl.client_name) LIKE :kw "
	                    : "bossName".equals(st)
	                        ? " LOWER(cl.boss_name) LIKE :kw "
	                        : " LOWER(cl.client_name) LIKE :kw OR LOWER(cl.boss_name) LIKE :kw "
	                ) +
	               ")")
	            : ""
	          );

	    var q = em.createNativeQuery(sql);

	    if (hasStatus) {
	        q.setParameter("status", cond.getStatus());
	    }
	    if (hasKw) {
	        q.setParameter("kw", "%" + cond.getSearchKeyword().toLowerCase() + "%");
	    }

	    Number n = (Number) q.getSingleResult();
	    return n.longValue();
	}
	
	//검색 조건과 페이지 범위에 맞춰 거래처 조회, 리스트로 반환 
	@Override
	public List<ClientDto> findPageClient(ClientDto cond) {
	    // 조건 플래그/타입
	    final boolean hasStatus = (cond.getStatus() != null);
	    final boolean hasKw = (cond.getSearchKeyword() != null && !cond.getSearchKeyword().isBlank());
	    final String st = (cond.getSearchType() == null || cond.getSearchType().isBlank())
	            ? "all" : cond.getSearchType();

	    // SELECT 컬럼 (인덱스 매핑 유지)
	    String selectCols =
	        "cl.client_code, cl.client_name, cl.saup_num, cl.boss_name, cl.client_type, cl.client_address, " +
	        "cl.client_tel, cl.client_emp_code, cl.client_status, cl.client_reg_code, cl.client_reg_date, " +
	        "e.emp_name AS client_emp_name, " +
	        "e.emp_tel  AS client_emp_tel, " +
	        "br.CD_CONTENTS AS client_type_br";

	    // WHERE 절
	    String where =
	        " WHERE 1=1 " +
	        (hasStatus ? " AND cl.client_status = :status " : "") +
	        (hasKw
	            ? (" AND " +
	                ("clientName".equals(st)
	                    ? " LOWER(cl.client_name) LIKE :kw "
	                    : "bossName".equals(st)
	                        ? " LOWER(cl.boss_name) LIKE :kw "
	                        : " (LOWER(cl.client_name) LIKE :kw OR LOWER(cl.boss_name) LIKE :kw) "
	                  )
	              )
	            : ""
	          );

	    // 내부 base 쿼리
	    String base =
	        "SELECT " + selectCols + " " +
	        "  FROM client_tb cl " +
	        "  LEFT JOIN emp   e  ON cl.client_emp_code = e.emp_code " +
	        "  LEFT JOIN bunryu br ON cl.client_type = br.MCD AND br.BCD = 400 " +
	           where +
	        " ORDER BY cl.client_code ASC";

	    // 페이징(Rownum) 래핑
	    String sql =
	        "SELECT * FROM ( " +
	        "  SELECT c.*, ROWNUM rn FROM ( " + base + " ) c " +
	        ") WHERE rn BETWEEN :start AND :end";

	    var q = em.createNativeQuery(sql);

	    if (hasStatus) q.setParameter("status", cond.getStatus());
	    if (hasKw)     q.setParameter("kw", "%" + cond.getSearchKeyword().toLowerCase() + "%");

	    q.setParameter("start", cond.getStart());
	    q.setParameter("end",   cond.getEnd());

	    @SuppressWarnings("unchecked")
	    List<Object[]> resultList = q.getResultList();

	    return resultList.stream().map(row -> {
	        ClientDto dto = new ClientDto();
	        dto.setClient_code(((Number) row[0]).intValue());
	        dto.setClient_name((String) row[1]);
	        dto.setSaup_num((String) row[2]);
	        dto.setBoss_name((String) row[3]);
	        dto.setClient_type(((Number) row[4]).intValue());
	        dto.setClient_address((String) row[5]);
	        dto.setClient_tel((String) row[6]);
	        dto.setClient_emp_code(((Number) row[7]).intValue());
	        dto.setClient_status(((Number) row[8]).intValue());
	        dto.setClient_reg_code(((Number) row[9]).intValue());
	        dto.setClient_reg_date(((java.sql.Timestamp) row[10]).toLocalDateTime());
	        dto.setClient_emp_name((String) row[11]);
	        dto.setClient_emp_tel((String) row[12]);
	        dto.setClient_type_br((String) row[13]);
	        return dto;
	    }).toList();
	}


	//전체 거래처 검색 
	@Override
	public List<ClientDto> findAllClient() {
		List<Client> clientEntityList = em.createQuery("select c from Client c", Client.class).getResultList();
		return clientEntityList.stream()
			.map(ClientDto::new)
			.collect(Collectors.toList());
	}

	//거래처 등록
	@Override
	public Client clientSave(Client client) {
		
	 	//현재 세션들고있는 사람을 등록자로
    	int curEmp = SecurityUtil.currentEmpCode();
    	client.changeClient_reg_code(curEmp);
    	
		// 1. 거래처 데이터 등록 
		em.persist(client);
		em.flush();
		
		// 2.전화번호 마지막 4글자 가져와서 비밀번호로  
		  String tel = client.getClient_tel();
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
	        account.setClient_code(client.getClient_code());              // Client_CODE 저장
	        account.setUsername(String.valueOf(client.getClient_code())); // USERNAME = Client_code
	        account.setPassword(encoded);                     			  // PASSWORD = hash(last4)
	        //공급처면 ROLE_CLIENT 가맹점이면 ROLE_CLIENT2로 저장  
	        if (client.getClient_type() == 2) 
	        { 
	            account.setRoles("ROLE_CLIENT");
	        } 
	        else if (client.getClient_type() == 3) 
	        {
	            account.setRoles("ROLE_CLIENT2");
	        }
	        em.persist(account); 

		return client;
	}
	
    //전화번호 마지막 4글자 따오는 로직 
	  private String extractLast4Digits(String tel) {
	        String digits = tel.replaceAll("\\D", "");
	        int len = digits.length();
	        if (len < 4) return "";
	        return digits.substring(len - 4);
	    }
	    
	//거래처코드로 검색 
	@Override
	public ClientDto findByClient_code(int client_code) {
		String nativeSql =
			"SELECT cl.client_code, cl.client_name, cl.saup_num, cl.boss_name, cl.client_type, cl.client_address, " +
			"       cl.client_tel, cl.client_emp_code, cl.client_status, cl.client_reg_code, cl.client_reg_date, " +
			"       e.emp_name AS client_emp_name, " +
			"       e.emp_tel AS client_emp_tel, " +
			"               br.CD_CONTENTS AS client_type_br " +
			"FROM client_tb cl " +  // 
			"LEFT JOIN emp e ON cl.client_emp_code = e.emp_code " +
			"LEFT JOIN bunryu br ON cl.client_type = br.MCD AND br.BCD = 400 " +
			"WHERE cl.client_code = :client_code";

		Object[] row = (Object[]) em.createNativeQuery(nativeSql)
			.setParameter("client_code", client_code)
			.getSingleResult();

		ClientDto dto = new ClientDto();
		dto.setClient_code(((Number) row[0]).intValue());
		dto.setClient_name((String) row[1]);
		dto.setSaup_num((String) row[2]);
		dto.setBoss_name((String) row[3]);
		dto.setClient_type(((Number) row[4]).intValue());
		dto.setClient_address((String) row[5]);
		dto.setClient_tel((String) row[6]);
		dto.setClient_emp_code(((Number) row[7]).intValue());
		dto.setClient_status(((Number) row[8]).intValue());
		dto.setClient_reg_code(((Number) row[9]).intValue());
		dto.setClient_reg_date(((java.sql.Timestamp) row[10]).toLocalDateTime());
		dto.setClient_emp_name((String) row[11]);
		dto.setClient_emp_tel((String) row[12]);
		dto.setClient_type_br((String) row[13]);
		return dto;
	}

	//거래처 정보 수정 
	@Transactional
	@Override
	public ClientDto updateClient(ClientDto clientDto) {
		String updateSql =
			"UPDATE client_tb SET " +  
			"  client_name = :name, " +
			"  saup_num = :saup, " +
			"  boss_name = :boss, " +
			"  client_type = :type, " +
			"  client_address = :address, " +
			"  client_tel = :tel, " +
			"  client_emp_code = :empCode, " +
			"  client_status = :status " +
			"WHERE client_code = :code";

		em.createNativeQuery(updateSql)
			.setParameter("name", clientDto.getClient_name())
			.setParameter("saup", clientDto.getSaup_num())
			.setParameter("boss", clientDto.getBoss_name())
			.setParameter("type", clientDto.getClient_type())
			.setParameter("address", clientDto.getClient_address())
			.setParameter("tel", clientDto.getClient_tel())
			.setParameter("empCode", clientDto.getClient_emp_code())
			.setParameter("status", clientDto.getClient_status())
			.setParameter("code", clientDto.getClient_code())
			.executeUpdate();
		
		//업데이트 후 필요에 따른  ROLE. 휴업중이면 guest 2면 공급처·3이면 가맹점 
		 String targetRole = null;
		    int status = clientDto.getClient_status();
		    int type   = clientDto.getClient_type();
		    
		    	//휴업
		        if (status == 1) {
		            targetRole = "ROLE_GUEST";
		        } 
		        //폐점
		        else if (status == 2) {
		            targetRole = "ROLE_GUEST";
		        } 	
		        //정상 영업중
		        else if (status == 0) {
		        	//type=2일경우 공급처 type=3 거래처
		            if 		(type == 2) targetRole = "ROLE_CLIENT";
		            else if (type == 3) targetRole = "ROLE_CLIENT2";
		        }
		    

		    //roles 업데이트 (client_code 참고)
		    if (targetRole != null) {
		        final String ROLE_TABLE = "Account";
		        em.createNativeQuery(
		            "UPDATE " + ROLE_TABLE + " SET roles = :roles WHERE client_code = :clientCode"
		        )
		        .setParameter("roles", targetRole)
		        .setParameter("clientCode", clientDto.getClient_code())
		        .executeUpdate();
		    }

		return findByClient_code(clientDto.getClient_code());
	}

}
