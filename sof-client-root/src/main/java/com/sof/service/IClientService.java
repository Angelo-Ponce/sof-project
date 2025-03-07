package com.sof.service;

import com.sof.model.Client;

import java.util.Optional;

public interface IClientService extends ICRUDService<Client, Long> {

    Optional<Client> findClientByClientId(String clientId);
    Client save(Client client, String user);
    Client update(Long id, Client client, String user);
    void deleteLogic(Long id, String user);
}
