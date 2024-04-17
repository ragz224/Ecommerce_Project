package com.raghu.productservice.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String name;

//    @JsonIgnoreProperties("category")
//    @JsonBackReference
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> product;
}
