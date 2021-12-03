package com.itmoura.api.petshop.model.entity;

import com.itmoura.api.petshop.model.dto.OwnerDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "owner")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Owner {

    @Id
    private String ownerId;
    private String name;
    private String age;
    private String address;
    private String cpf;

    public static Owner convert(OwnerDTO ownerDTO) {
        Owner owner = new Owner();
        BeanUtils.copyProperties(ownerDTO, owner);
        return owner;
    }
}
