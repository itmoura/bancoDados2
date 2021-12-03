package com.itmoura.api.petshop.service;

import com.itmoura.api.petshop.model.dto.OwnerDTO;
import com.itmoura.api.petshop.model.entity.Owner;
import com.itmoura.api.petshop.repository.OwnerRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.itmoura.api.petshop.model.entity.Owner.convert;

@Service
@Log4j2
public class OwnerService {

    private OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public void save(OwnerDTO ownerDTO) {
        log.debug("Save to Owner: {}", ownerDTO);
        String uri = "mongodb+srv://admin:aSWQd6PICxRKgJbu@cluster0.zzgej.mongodb.net/petshop?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("petshop");
        MongoCollection<Document> owner = database.getCollection("owner");

        owner.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("name", ownerDTO.getName())
                .append("age", ownerDTO.getAge())
                .append("address", ownerDTO.getAddress())
                .append("cpf", ownerDTO.getCpf()));
    }

    public Owner convertAndSave(OwnerDTO ownerDTO) {
        return ownerRepository.save(convert(ownerDTO));
    }

    public OwnerDTO getOwner(String ownerId) throws Exception {
        /*
            db.owner.findOne({'ownerId': ownerId})
        */
        return ownerRepository.findById(ownerId).map(OwnerDTO::convert).orElseThrow(() -> new Exception("Dono não encontrado"));
    }

    public List<OwnerDTO> getAllOwner() {
        /*
            db.owner.find()
        */
        return ownerRepository.findAll().stream().map(OwnerDTO::convert).collect(Collectors.toList());
    }

    public void removeOwner(String ownerId){
        /*
            db.owner.deleteOne({'ownerId': ownerId})
        */
        ownerRepository.deleteById(ownerId);
    }

    public OwnerDTO update(String ownerId, OwnerDTO ownerDTO) {
        ownerRepository.findById(ownerId).ifPresent(owner -> ownerDTO.setOwnerId(owner.getOwnerId()));
        log.debug("Update to pet: {}", ownerDTO);
        return ownerDTO.convert(convertAndSave(ownerDTO));
    }

    public List<String> getPetAggregation(String ownerId) {
        String uri = "mongodb+srv://admin:aSWQd6PICxRKgJbu@cluster0.zzgej.mongodb.net/petshop?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("petshop");
        MongoCollection<Document> pet = database.getCollection("pet");

        List<String> pets = new ArrayList<>();

        pet.aggregate(
            Arrays.asList(
                Aggregates.match(Filters.eq("ownerId", ownerId))
            )
        ).forEach(doc -> pets.add(doc.toJson()));

        return pets;
    }
}
