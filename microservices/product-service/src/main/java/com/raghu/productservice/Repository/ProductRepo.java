package com.raghu.productservice.Repository;

import com.raghu.productservice.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {

    Page<Product> findAllByTitle(String query, Pageable pageable);

    Page<Product> findAllByTitleContaining(String query, Pageable pageable);
}
