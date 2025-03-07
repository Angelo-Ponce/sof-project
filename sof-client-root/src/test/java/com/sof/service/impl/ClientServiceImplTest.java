package com.sof.service.impl;

import com.sof.exception.ModelNotFoundException;
import com.sof.model.Client;
import com.sof.repository.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private IClientRepository repository;

    private final Long personId = 1L;
    private final String user = "Angelo";
    private Client mockClient;

    @BeforeEach
    void setUp() {
        mockClient = new Client();
        mockClient.setPersonId(personId);
        mockClient.setIdentification("0912458963");
        mockClient.setName("Angelo");
        mockClient.setGender("Male");
        mockClient.setAge(20);
        mockClient.setAddress("Ecuador");
        mockClient.setPhone("0999");
        mockClient.setClientId("angelo");
        mockClient.setPassword("123456");
        mockClient.setStatus(true);
    }

    @Test
    void givenGetRepository_WhenCalled_ThenReturnCorrectRepositoryInstance() {
        assertEquals(repository, clientService.getRepository());
    }

    @Test
    void givenFindClientByClientId_WhenEntityExists_ThenReturnEntity(){
        when(repository.findClientByClientId(mockClient.getClientId())).thenReturn(Optional.of(mockClient));
        Optional<Client> result = clientService.findClientByClientId(mockClient.getClientId());
        assertEquals(mockClient.getClientId(), result.get().getClientId());
        verify(repository, times(1)).findClientByClientId(mockClient.getClientId());
    }

    @Test
    void givenSave_ThenSuccessfullySaveTheEntity_WhenTheEntityHasData(){
        when(repository.save(any(Client.class))).thenReturn(mockClient);
        Client result = clientService.save(mockClient, user);
        assertNotNull(result);
        assertEquals(mockClient.getClientId(), result.getClientId());
        verify(repository, times(1)).save(mockClient);
    }

    @Test
    void givenUpdateEntity_WhenEntityExists_ThenReturnUpdatedEntity(){
        when(repository.findById(personId)).thenReturn(Optional.of(mockClient));
        when(repository.save(any(Client.class))).thenReturn(mockClient);
        Client client = clientService.update(personId, mockClient, user);
        assertNotNull(client);
        verify(repository, times(1)).findById(personId);
        verify(repository, times(1)).save(any());
    }

    @Test
    void givenDeleteLogic_WhenClientExists_ThenMarkClientAsInactive() {
        when(repository.findById(personId)).thenReturn(Optional.of(mockClient));

        clientService.deleteLogic(personId, user);

        assertEquals(Boolean.FALSE, mockClient.getStatus());
        assertEquals(user, mockClient.getLastModifiedByUser());
        verify(repository, times(1)).save(mockClient);
    }

    @Test
    void givenDeleteLogic_WhenClientDoesNotExist_ThenThrowModelNotFoundException() {

        when(repository.findById(personId)).thenReturn(Optional.empty());

        assertThrows(
                ModelNotFoundException.class,
                () -> clientService.deleteLogic(personId, user),
                "ID not found: " + personId
        );

        verify(repository, never()).save(any());
    }

}