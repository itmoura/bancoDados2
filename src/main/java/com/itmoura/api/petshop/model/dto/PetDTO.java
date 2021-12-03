package com.itmoura.api.petshop.model.dto;

import com.itmoura.api.petshop.model.entity.Pet;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PetDTO implements Serializable {

    private static final long serialVersionUID = 8094605274048833722L;

    private String petId;
    private String name;
    private String age;
    private String breed;
    private String ownerId;

    public static PetDTO convert(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }
}
