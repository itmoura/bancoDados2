package com.itmoura.api.petshop.resource;

import com.itmoura.api.petshop.model.dto.OwnerDTO;
import com.itmoura.api.petshop.model.dto.PetDTO;
import com.itmoura.api.petshop.service.OwnerService;
import com.itmoura.api.petshop.service.PetService;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController("API para cadastro de Dono Pet")
@RequestMapping("/api/v1/owner")
public class Owner {
    @Autowired
    private OwnerService ownerService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable("id") String ownerId) throws Exception {
        return ResponseEntity.ok(ownerService.getOwner(ownerId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createOwner(@RequestBody OwnerDTO ownerDTO) {
        ownerService.save(ownerDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> removeOwner(@PathVariable("id") String ownerId) {
        ownerService.removeOwner(ownerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OwnerDTO> updateOwner(@PathVariable("id") String ownerId, @RequestBody OwnerDTO ownerDTO) {
        return ResponseEntity.ok(ownerService.update(ownerId, ownerDTO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<OwnerDTO>> getAllOwner() throws Exception {
        return ResponseEntity.ok(ownerService.getAllOwner());
    }

    @GetMapping(value = "/aggregation/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getOwnerAggregation(@PathVariable("id") String ownerId) {
        return ResponseEntity.ok(ownerService.getPetAggregation(ownerId));
    }
}
