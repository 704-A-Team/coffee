package com.oracle.coffee.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.oracle.coffee.domain.Client;
import com.oracle.coffee.domain.Emp;
import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.repository.ClientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Long totalClient() {
	Long totalCount =  clientRepository.clientTotalcount();
	
		return totalCount;
	}
	
	@Override
	public List<ClientDto> clientList(ClientDto clientDto) {
	    List<ClientDto> clientRtnList = clientRepository.findPageClient(clientDto);	
	    
	    return clientRtnList;
	}
	
	@Override
	public List<ClientDto> clientAllList() {
		List<ClientDto> listClientAll  = clientRepository.findAllClient();
		
		return listClientAll;
	}

	@Override
	public int clientSave(ClientDto clientDto) {
		Client client = modelMapper.map(clientDto, Client.class);
		Client saveClient = clientRepository.clientSave(client);
		
		return saveClient.getClient_code();
	}

	@Override
	public ClientDto getSingleClient(int client_code) {

		return clientRepository.findByClient_code(client_code); 
	}

	@Override
	public ClientDto getSingleEmp(int client_code) {

	    return clientRepository.findByClient_code(client_code); 
	}

	@Override
	public ClientDto clientUpdate(ClientDto clientDto) {

		return clientRepository.updateClient(clientDto);
	}
	
}
