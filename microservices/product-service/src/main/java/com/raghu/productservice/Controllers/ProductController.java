package com.raghu.productservice.Controllers;

import com.raghu.productservice.DTOs.ExceptionDto;
import com.raghu.productservice.DTOs.GenericProductDto;
import com.raghu.productservice.Exceptions.InvalidCategoryException;
import com.raghu.productservice.Exceptions.NotFoundException;
import com.raghu.productservice.Exceptions.ProductNotFoundException;
import com.raghu.productservice.Models.Product;
import com.raghu.productservice.Security.TokenValidator;
import com.raghu.productservice.Services.FakeStoreProductService;
import com.raghu.productservice.Services.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/Products")
public class ProductController {

    ProductService productService;
    TokenValidator tokenValidator;



    @Autowired
    public ProductController( ProductService productService, TokenValidator tokenValidator) {
        this.productService = productService;
        this.tokenValidator = tokenValidator;
    }

//    @GetMapping
//    public List<GenericProductDto> getProducts() {
//        return productService.getAllProducts();
//
//
//    }
//
//    @GetMapping("{id}")
//    public GenericProductDto getProductsById(@PathVariable("id") Long id) throws NotFoundException {
//
//        return productService.getProductById(id);
//
//    }
//
//    @PutMapping("{id}")
//    public GenericProductDto updateProductById(@PathVariable("id") Long id, @RequestBody  GenericProductDto genericProductDto) {
//        return productService.updateProductById(id,genericProductDto);
//    }
//
//    @PostMapping()
//    public GenericProductDto createProduct(@RequestBody  GenericProductDto genericProductDto) {
//        return productService.uploadProduct(genericProductDto);
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<GenericProductDto> DeleteProductById(@PathVariable Long id) {
//        return new ResponseEntity<>(productService.DeleteProductById(id), HttpStatus.OK);
//    }


//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException) {
//        return new ResponseEntity<>( new ExceptionDto(HttpStatus.NOT_FOUND,notFoundException.getMessage()),
//        HttpStatus.NOT_FOUND);
//    }


    @PostMapping()
    public ResponseEntity<GenericProductDto> CreateProduct(@RequestBody GenericProductDto genericProductDto) throws InvalidCategoryException {
        return new ResponseEntity<>(productService.createProduct(genericProductDto),HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public GenericProductDto GetProductById(@PathVariable("id") String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) throws NotFoundException {

        System.out.println("going from controller");
        System.out.println("yes it is");
        GenericProductDto genericProductDto = productService.getProductById(id);
        if(genericProductDto==null) {
            return new GenericProductDto();
        }

        return genericProductDto;
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericProductDto> UpdateProductById(@PathVariable("id") String id,
                                                               @RequestBody GenericProductDto genericProductDto) throws NotFoundException {
        System.out.println("from update api");
        GenericProductDto genericProductDto1 = productService.UpdateProductById(genericProductDto, id);
        if(genericProductDto1==null) {
            return  null;
        }

        return new ResponseEntity<>(genericProductDto1,HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable("id") String id ) throws NotFoundException {
        return  new ResponseEntity<>(productService.deletProductById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GenericProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<GenericProductDto>> getAllProductsbyCategory(@PathVariable("category") String name) throws InvalidCategoryException {
        return new ResponseEntity<>(productService.getAllProductsByCategoryName(name),HttpStatus.OK);
    }

    @GetMapping("/category")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }


}
