package com.raghu.productservice.Inheriteance.MappedSuperClass;

import jakarta.persistence.Entity;

@Entity
public class Mentor extends User{
    private int avgRating;
}
