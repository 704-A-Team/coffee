package com.oracle.coffee.service;

import java.util.List;

import com.oracle.coffee.dto.ClientDto;

public interface ClientService {

	Long            		totalClient(ClientDto cond);  
	List<ClientDto> 		clientList(ClientDto cond);
	List<ClientDto> 		clientAllList();
	
	int 					clientSave(ClientDto clientDto);
	ClientDto 				getSingleClient(int client_code);
	ClientDto 				getSingleEmp(int client_code);
	ClientDto				clientUpdate(ClientDto clientDto);
	

	

}
