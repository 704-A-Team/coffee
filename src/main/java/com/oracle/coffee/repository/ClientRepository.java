package com.oracle.coffee.repository;

import java.util.List;

import com.oracle.coffee.domain.Client;
import com.oracle.coffee.dto.ClientDto;

public interface ClientRepository {

	Long             clientTotalcount(ClientDto cond);
    List<ClientDto>  findPageClient(ClientDto cond);     
    List<ClientDto>  findAllClient();

    Client           clientSave(Client client);
    ClientDto        findByClient_code(int client_code);
    ClientDto        updateClient(ClientDto clientDto);
}
