package com.oracle.coffee.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

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
				"				e.emp_tel AS client_emp_tel "  + 
				"        FROM client_tb cl " +  
				"        LEFT JOIN emp e ON cl.client_emp_code = e.emp_code " +
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
		em.persist(client);
		return client;
	}

	@Override
	public ClientDto findByClient_code(int client_code) {
		String nativeSql =
			"SELECT cl.client_code, cl.client_name, cl.saup_num, cl.boss_name, cl.client_type, cl.client_address, " +
			"       cl.client_tel, cl.client_emp_code, cl.client_status, cl.client_reg_code, cl.client_reg_date, " +
			"       e.emp_name AS client_emp_name, " +
			"       e.emp_tel AS client_emp_tel " +
			"FROM client_tb cl " +  // 
			"LEFT JOIN emp e ON cl.client_emp_code = e.emp_code " +
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
