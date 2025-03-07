package com.sof.service.impl;

import com.sof.exception.ModelNotFoundException;
import com.sof.model.Client;
import com.sof.repository.IClientRepository;
import com.sof.repository.IGenericRepository;
import com.sof.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends CRUDServiceImpl<Client, Long> implements IClientService {

    private final IClientRepository repository;

    @Override
    protected IGenericRepository<Client, Long> getRepository() {
        return repository;
    }

    @Override
    public Optional<Client> findClientByClientId(String clientId) {
        return repository.findClientByClientId(clientId);
    }

    @Override
    public Client save(Client client, String user) {
        client.setCreatedByUser(user);
        return repository.save(client);
    }

    @Override
    public Client update(Long id, Client client, String user) {
        return repository.findById(id)
                .map(clientEntity -> {
                    clientEntity.setIdentification(client.getIdentification());
                    clientEntity.setName(client.getName());
                    clientEntity.setGender(client.getGender());
                    clientEntity.setAge(client.getAge());
                    clientEntity.setAddress(client.getAddress());
                    clientEntity.setPhone(client.getPhone());
                    clientEntity.setClientId(client.getClientId());
                    clientEntity.setPassword(client.getPassword());
                    clientEntity.setStatus(client.getStatus());
                    clientEntity.setLastModifiedByUser(user);
                    return repository.save(clientEntity);
                }).orElseThrow(() -> new ModelNotFoundException("ID not found: " + id));
    }

    @Override
    public void deleteLogic(Long id, String user) {
        Client client = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID not found: " + id));
        client.setStatus(Boolean.FALSE);
        client.setLastModifiedByUser(user);
        repository.save(client);
    }
}
