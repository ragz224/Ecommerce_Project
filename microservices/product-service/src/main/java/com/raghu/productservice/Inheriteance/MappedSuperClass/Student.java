package com.raghu.productservice.Inheriteance.MappedSuperClass;

import jakarta.persistence.Entity;

@Entity
public class Student extends User {
    private double psp;
    private long marks;
}
