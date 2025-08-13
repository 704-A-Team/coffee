package com.oracle.coffee.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.domain.Account;
import com.oracle.coffee.domain.Client;
import com.oracle.coffee.dto.ClientDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

	private final EntityManager em;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Long clientTotalcount() {
		TypedQuery<Long> query = em.createQuery("select count(c) from Client c", Long.class);
		return query.getSingleResult();
	}
	
		@Override
		public List<ClientDto> findPageClient(ClientDto clientDto) {
			String nativeSql =
				"SELECT * FROM ( " +
				"    SELECT ROWNUM rn, c.* FROM ( " +
				"        SELECT cl.client_code, cl.client_name, cl.saup_num, cl.boss_name, cl.client_type, cl.client_address, " +
				"               cl.client_tel, cl.client_emp_code, cl.client_status, cl.client_reg_code, cl.client_reg_date, " +
				"               e.emp_name AS client_emp_name, " +
				"				e.emp_tel AS client_emp_tel, "  +
				"               br.CD_CONTENTS AS client_type_br " +
				"        FROM client_tb cl " +  
				"        LEFT JOIN emp e ON cl.client_emp_code = e.emp_code " +
				"        LEFT JOIN bunryu br ON cl.client_type = br.MCD AND br.BCD = 400 " +
				"        ORDER BY cl.client_code " +
				"    ) c " +
				") " +
				"WHERE rn BETWEEN :start AND :end";

		Query query = em.createNativeQuery(nativeSql);
		query.setParameter("start", clientDto.getStart());
		query.setParameter("end", clientDto.getEnd());

		List<Object[]> resultList = query.getResultList();

		return resultList.stream().map(row -> {
			ClientDto dto = new ClientDto();
			dto.setClient_code(((Number) row[1]).intValue());
			dto.setClient_name((String) row[2]);
			dto.setSaup_num((String) row[3]);
			dto.setBoss_name((String) row[4]);
			dto.setClient_type(((Number) row[5]).intValue());
			dto.setClient_address((String) row[6]);
			dto.setClient_tel((String) row[7]);
			dto.setClient_emp_code(((Number) row[8]).intValue());
			dto.setClient_status(((Number) row[9]).intValue());
			dto.setClient_reg_code(((Number) row[10]).intValue());
			dto.setClient_reg_date(((java.sql.Timestamp) row[11]).toLocalDateTime());
			dto.setClient_emp_name((String) row[12]);
			dto.setClient_emp_tel((String) row[13]);
			dto.setClient_type_br((String) row[14]);
			return dto;	
		}).collect(Collectors.toList());
	}

	@Override
	public List<ClientDto> findAllClient() {
		List<Client> clientEntityList = em.createQuery("select c from Client c", Client.class).getResultList();
		return clientEntityList.stream()
			.map(ClientDto::new)
			.collect(Collectors.toList());
	}

	@Override
	public Client clientSave(Client client) {
		// 1. 거래처 등록
		em.persist(client);
		em.flush();
		
		// 2.거래처 
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
	        account.setClient_code(client.getClient_code());              // EMP_CODE 저장
	        account.setUsername(String.valueOf(client.getClient_code())); // USERNAME = emp_code
	        account.setPassword(encoded);                       // PASSWORD = hash(last4)
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
	
	  private String extractLast4Digits(String tel) {
	        String digits = tel.replaceAll("\\D", "");
	        int len = digits.length();
	        if (len < 4) return "";
	        return digits.substring(len - 4);
	    }
	    

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

		return findByClient_code(clientDto.getClient_code());
	}

}
