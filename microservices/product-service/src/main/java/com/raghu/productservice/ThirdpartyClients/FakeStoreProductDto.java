package com.raghu.productservice.ThirdpartyClients;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FakeStoreProductDto {
    private int id;
    private UUID uuid;
    private String description;

    private String category;
    private double price;
    private String title;
    private String image;
}
