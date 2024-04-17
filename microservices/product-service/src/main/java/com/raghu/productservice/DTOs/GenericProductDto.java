package com.raghu.productservice.DTOs;

import com.raghu.productservice.Models.Category;
import com.raghu.productservice.Models.Price;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GenericProductDto {
    private String id;
    private String title;
    private String price;
    private String description;
    private String image;
    private String category;

}
