package com.raghu.productservice.ThirdpartyClients;

import com.raghu.productservice.Models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SelfStoreDto {
    private int id;
    private UUID uuid;
    private String title;
    private double price;
    private String description;
    private String image;
    private Category category;
}
