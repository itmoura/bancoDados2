package com.itmoura.api.petshop.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itmoura.api.petshop.model.dto.PetDTO;
import com.itmoura.api.petshop.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController("API para cadastro de PET")
@RequestMapping("/api/v1/pet")
public class Pet {
    @Autowired
    private PetService petService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PetDTO> getPet(@PathVariable("id") String petId) throws Exception {
        return ResponseEntity.ok(petService.getPet(petId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createPet(@RequestBody PetDTO petDTO) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(petService.save(petDTO).getPetId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> removePet(@PathVariable("id") String petId) {
        petService.removePet(petId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PetDTO> updatePet(@PathVariable("id") String petId, @RequestBody PetDTO petDTO) {
        return ResponseEntity.ok(petService.update(petId, petDTO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PetDTO>> getAllPet() throws Exception {
        return ResponseEntity.ok(petService.getAllPet());
    }

    @GetMapping(value = "/name",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getPetName(@RequestParam("name") String name) throws Exception {
        return ResponseEntity.ok(petService.getPetName(name));
    }
}
