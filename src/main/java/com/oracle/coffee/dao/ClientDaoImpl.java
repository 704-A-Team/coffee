package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.SWClientDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClientDaoImpl implements ClientDao {
	private final SqlSession session;
	
	@Override
	public List<SWClientDto> getClientInfo(int client_type) {
System.out.println("ClientDaoImpl getClientInfo start...");
		
		List<SWClientDto> clientList = session.selectList("clientInfo", client_type);
	
		return clientList;
	}

}
