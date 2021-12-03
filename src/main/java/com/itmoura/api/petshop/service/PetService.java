package com.itmoura.api.petshop.service;

import com.itmoura.api.petshop.model.dto.PetDTO;
import com.itmoura.api.petshop.model.entity.Pet;
import com.itmoura.api.petshop.repository.PetRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.itmoura.api.petshop.model.entity.Pet.convert;

@Service
@Log4j2
public class PetService {

    private PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetDTO save(PetDTO petDTO) {
        log.debug("Save to pet: {}", petDTO);
        return PetDTO.convert(convertAndSave(petDTO));
    }

    public Pet convertAndSave(PetDTO petDTO) {
        /*
            db.pet.insertOne(
                {
                    "petId": petId (auto compleete),
                    "name": NomePet,
                    "age": IdadePet,
                    "breed": RaçaPet,
                }
            )
        */
        return petRepository.save(convert(petDTO));
    }

    public PetDTO getPet(String petId) throws Exception {
        /*
            db.pet.findOne({'petId': petId})
        */
        return petRepository.findById(petId).map(PetDTO::convert).orElseThrow(() -> new Exception("Pet não encontrado"));
    }

    public List<PetDTO> getAllPet() {
        /*
            db.pet.find()
        */
        return petRepository.findAll().stream().map(PetDTO::convert).collect(Collectors.toList());
    }

    public void removePet(String petId){
        /*
            db.pet.deleteOne({'petId': petId})
        */
        petRepository.deleteById(petId);
    }

    public PetDTO update(String petId, PetDTO petDTO) {
        petRepository.findById(petId).ifPresent(pet -> petDTO.setPetId(pet.getPetId()));
        log.debug("Update to pet: {}", petDTO);
        return PetDTO.convert(convertAndSave(petDTO));
    }

    public List<String> getPetName(String name) {
        String uri = "mongodb+srv://admin:aSWQd6PICxRKgJbu@cluster0.zzgej.mongodb.net/petshop?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("petshop");
        MongoCollection<Document> pet = database.getCollection("pet");

        List<String> pets = new ArrayList<>();

        BasicDBObject regexQuery = new BasicDBObject();
        regexQuery.put("name", new BasicDBObject("$regex", ".*"+name+".*").append("$options", "i"));

        for (Document document : pet.find(regexQuery)) {
            System.out.println(document);
            pets.add(String.valueOf(document));
        }

        return pets;
    }
}
