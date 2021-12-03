package com.itmoura.api.petshop.model.entity;

import com.itmoura.api.petshop.model.dto.PetDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pet")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pet {

    @Id
    private String petId;
    private String name;
    private String age;
    private String breed;
    private String ownerId;

    public static Pet convert(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
