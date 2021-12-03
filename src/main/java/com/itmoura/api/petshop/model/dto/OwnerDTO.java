package com.itmoura.api.petshop.model.dto;

import com.itmoura.api.petshop.model.entity.Owner;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OwnerDTO implements Serializable {

    private static final long serialVersionUID = -2680320517802653005L;

    private String ownerId;
    private String name;
    private String age;
    private String address;
    private String cpf;

    public static OwnerDTO convert(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();
        BeanUtils.copyProperties(owner, ownerDTO);
        return ownerDTO;
    }
}
