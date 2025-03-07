package com.sof.repository;

import com.sof.model.Client;

import java.util.Optional;

public interface IClientRepository extends IGenericRepository<Client, Long> {
    Optional<Client> findClientByClientId(String clientId);
}
