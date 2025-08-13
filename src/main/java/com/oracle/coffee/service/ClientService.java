package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.ClientDto;

public interface ClientService {

	Long 					totalClient();
	List<ClientDto> 		clientList(ClientDto clientDto);
	List<ClientDto> 		clientAllList();
	
	int 					clientSave(ClientDto clientDto);
	ClientDto 				getSingleClient(int client_code);
	ClientDto 				getSingleEmp(int client_code);
	ClientDto				clientUpdate(ClientDto clientDto);
	

	

}
