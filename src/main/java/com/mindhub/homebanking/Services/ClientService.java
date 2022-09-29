package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    public List<Client> getClients();

    public Client getClientById(long id);

    public Client getClientByEmail(String email);

    public void saveClient(Client client);

}
