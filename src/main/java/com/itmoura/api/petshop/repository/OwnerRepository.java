package com.itmoura.api.petshop.repository;

import com.itmoura.api.petshop.model.entity.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OwnerRepository extends MongoRepository<Owner, String> {
}
