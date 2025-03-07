package com.sof.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sof.model.Client;
import com.sof.service.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IClientService service;

    private static final String USER = "Angelo";
    private final Long personId = 1L;
    private Client mockClient;

    @BeforeEach
    void setUp() {
        reset(service);
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
    void testFindAllClients() throws Exception {
        Client client1 = new Client("CL001", "password1", true);
        client1.setPersonId(1L);
        Client client2 = new Client("CL002", "password2", true);
        client2.setPersonId(2L);

        when(service.findAll()).thenReturn(List.of(client1, client2));

        mockMvc.perform(get("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].clientId", is("CL001")))
                .andExpect(jsonPath("$[1].clientId", is("CL002")));

        verify(service, times(1)).findAll();
    }

    @Test
    void testFindClientById() throws Exception {
        Client client = new Client("CL001", "password1", true);
        client.setPersonId(1L);

        when(service.findById(1L)).thenReturn(client);

        mockMvc.perform(get("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is("CL001")))
                .andExpect(jsonPath("$.personId", is(1)));

        verify(service, times(1)).findById(1L);
    }

    @Test
    void testFindClientByClientId() throws Exception {
        Client client = new Client("CL001", "password1", true);
        client.setPersonId(1L);

        when(service.findClientByClientId("CL001")).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/v1/clientes/clientId/CL001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is("CL001")))
                .andExpect(jsonPath("$.personId", is(1)));

        verify(service, times(1)).findClientByClientId("CL001");
    }

    @Test
    void testSaveClient() throws Exception {
        when(service.save(any(Client.class), eq(USER))).thenReturn(mockClient);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockClient)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/v1/clientes/1"));

        verify(service, times(1)).save(any(Client.class), eq(USER));
    }

    @Test
    void testUpdateClient() throws Exception {
        when(service.update(eq(1L), any(Client.class), eq(USER))).thenReturn(mockClient);

        mockMvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockClient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is("angelo"))) // Verificar los datos actualizados
                .andExpect(jsonPath("$.password", is("123456")))
                .andExpect(jsonPath("$.status", is(true)));

        verify(service, times(1)).update(eq(1L), any(Client.class), eq(USER));
    }

    @Test
    void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteLogic(1L, USER);
    }
}