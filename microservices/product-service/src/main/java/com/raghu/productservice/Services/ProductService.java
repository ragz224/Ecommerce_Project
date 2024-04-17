package com.raghu.productservice.Services;

import com.raghu.productservice.DTOs.GenericProductDto;
import com.raghu.productservice.Exceptions.InvalidCategoryException;
import com.raghu.productservice.Exceptions.NotFoundException;
import com.raghu.productservice.Exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ProductService {

//    GenericProductDto getProductById(Long id) throws NotFoundException;
//
//    GenericProductDto uploadProduct(GenericProductDto genericProductDto);
//
//    GenericProductDto updateProductById(Long id,GenericProductDto genericProductDto);
//
//     GenericProductDto DeleteProductById(Long id);
//
//    List<GenericProductDto> getAllProducts();

    GenericProductDto createProduct(GenericProductDto genericProductDto) throws InvalidCategoryException;
    GenericProductDto getProductById(String id) throws NotFoundException;

    GenericProductDto UpdateProductById( GenericProductDto genericProductDto, String id) throws NotFoundException;

    GenericProductDto deletProductById(String id) throws NotFoundException;

    List<GenericProductDto> getAllProducts();

    List<GenericProductDto> getAllProductsByCategoryName(String name) throws InvalidCategoryException;

    List<String> getAllCategories();


}
