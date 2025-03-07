package com.sof.controller;

import com.sof.dto.ClientDTO;
import com.sof.model.Client;
import com.sof.service.IClientService;
import com.sof.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService service;

    private final MapperUtil mapperUtil;

    private static final String USER = "Angelo";

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> list = mapperUtil.mapList(service.findAll(), ClientDTO.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable("id") Long id){
        Client obj = service.findById(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mapperUtil.map(obj, ClientDTO.class));
    }

    @GetMapping("clientId/{clientId}")
    public ResponseEntity<ClientDTO> findClientByClientId(@PathVariable("clientId") String clientId){
        return service.findClientByClientId(clientId)
                .map(client -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapperUtil.map(client, ClientDTO.class))
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ClientDTO dto){
        Client clientEntity = mapperUtil.map(dto, Client.class);
        Client obj = service.save(clientEntity, USER);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update( @PathVariable("id") Long id, @RequestBody ClientDTO dto){
        Client obj = service.update(id, mapperUtil.map(dto, Client.class), USER);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mapperUtil.map(obj, ClientDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete( @PathVariable("id") Long id){
        service.deleteLogic(id, USER);
        return ResponseEntity.noContent().build();
    }
}
