package com.raghu.productservice.Mappers;

import com.raghu.productservice.DTOs.GenericProductDto;
import com.raghu.productservice.Models.Product;

import java.util.UUID;

public class DTOMapper {

    public static Product GenericToProductDtoMapper(GenericProductDto genericProductDto) {
        Product product = new Product();
        if(genericProductDto.getId()!=null) {
            product.setUuid(UUID.fromString(genericProductDto.getId()));
        }
        product.setTitle(genericProductDto.getTitle());
//        product.setPrice(genericProductDto.getPrice());
        product.setDescription(genericProductDto.getDescription());
        product.setImage(genericProductDto.getImage());

       return product;
    }


    public static GenericProductDto ProducttoGenericDtoMapper(Product product) {
        GenericProductDto genericProductDto = new GenericProductDto();
        if(product.getUuid()!=null) {
            genericProductDto.setId(product.getUuid().toString());
        }
        else {
            return null;
        }
        genericProductDto.setPrice(product.getPrice().getName());
        genericProductDto.setCategory(product.getCategory().getName());
        genericProductDto.setDescription(product.getDescription());
        genericProductDto.setImage(product.getImage());
        genericProductDto.setTitle(product.getTitle());
        return genericProductDto;
    }
}
