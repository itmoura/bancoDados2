package com.itmoura.api.petshop.repository;

import com.itmoura.api.petshop.model.entity.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PetRepository extends MongoRepository<Pet, String> {
    Optional<Pet> findByPetId(Long petId);
}
