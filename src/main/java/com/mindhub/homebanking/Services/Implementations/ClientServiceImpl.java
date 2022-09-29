package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> getClients() {

        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(long id){

        return clientRepository.findById(id).get();
    }

    @Override
    public Client getClientByEmail(String email){

        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client){

        clientRepository.save(client);
    }
}
