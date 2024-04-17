package com.userservice.Models;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuidgenerator", strategy = "uuid2")
//    @Column(name = "id",columnDefinition = "binary(16)",nullable = false, updatable = false)
//    private UUID uuid;

}
