package com.raghu.productservice.Services;

import com.raghu.productservice.DTOs.GenericProductDto;
import com.raghu.productservice.Exceptions.InvalidCategoryException;
import com.raghu.productservice.Exceptions.NotFoundException;
import com.raghu.productservice.Exceptions.ProductNotFoundException;
import com.raghu.productservice.Mappers.DTOMapper;
import com.raghu.productservice.Models.Category;
import com.raghu.productservice.Models.Price;
import com.raghu.productservice.Models.Product;
import com.raghu.productservice.Repository.CategoryRepo;
import com.raghu.productservice.Repository.PriceRepo;
import com.raghu.productservice.Repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.util.TypeCollector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Primary
@Service("selfProductService")
public class SelfProductService implements ProductService {

    ProductRepo productRepo;
    CategoryRepo categoryRepo;
    PriceRepo priceRepo;

    @Autowired
    public SelfProductService(ProductRepo productRepo, CategoryRepo categoryRepo, PriceRepo priceRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.priceRepo = priceRepo;
    }

//    @Override
//    public GenericProductDto createProduct(GenericProductDto genericProductDto) throws InvalidCategoryException {
//
//        Product product = DTOMapper.GenericToProductDtoMapper(genericProductDto);
//        String category = genericProductDto.getCategory();
//        if (category == null) {
//            throw new RuntimeException("Category not provided");
//        }
//
//        String price = genericProductDto.getPrice();
//
//        if(price == null) {
//            throw new RuntimeException("price not provided");
//        }
//
//        Optional<Category> Opscategory = categoryRepo.findByName(genericProductDto.getCategory());
//        if (Opscategory.isEmpty()) {
//            Category category2 = new Category();
//
//        category2.setName(genericProductDto.getCategory());
//        Category savedcategory = categoryRepo.save(category2);
//        product.setCategory(savedcategory);
////            throw new InvalidCategoryException("The category: " + genericProductDto.getCategory() + " doesn't exists.");
//       }
//        else {
//            Category category1 = Opscategory.get();
//            product.setCategory(category1);
//        }
//
//        Optional<Price> Opsprice = priceRepo.findByName(genericProductDto.getPrice());
//
//        if (Opsprice.isEmpty()) {
//            Price price2 = new Price();
//            price2.setName(genericProductDto.getPrice());
//           Price savedProduct = priceRepo.save(price2);
//            product.setPrice(savedProduct);
////            throw new InvalidCategoryException("The price: " + genericProductDto.getPrice() + " doesn't exists.");
//        }
//        else {
//            Price price1 = Opsprice.get();
//            product.setPrice(price1);
//        }
//
//        Product savedProduct = productRepo.save(product);
//
//        return DTOMapper.ProducttoGenericDtoMapper(savedProduct);
//    }



    @Override
    @Transactional
    public GenericProductDto createProduct(GenericProductDto genericProductDto) throws InvalidCategoryException {
        // Validate DTO fields
        String category = genericProductDto.getCategory();
        if (category == null || category.trim().isEmpty()) {
            throw new RuntimeException("Category not provided");
        }

        String price = genericProductDto.getPrice();
        if (price == null || price.trim().isEmpty()) {
            throw new RuntimeException("Price not provided");
        }

        // Create the product entity from the DTO
        Product product = DTOMapper.GenericToProductDtoMapper(genericProductDto);

        // Retrieve or create and save the Category
        Optional<Category> opsCategory = categoryRepo.findByName(category);
        Category savedCategory = opsCategory.orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName(category);
            return categoryRepo.save(newCategory);
        });

        // Set the Category in the Product entity
        product.setCategory(savedCategory);

        // Retrieve or create and save the Price
        Optional<Price> opsPrice = priceRepo.findByName(price);
        Price savedPrice = opsPrice.orElseGet(() -> {
            Price newPrice = new Price();
            newPrice.setName(price);
            return priceRepo.save(newPrice);
        });

        // Set the Price in the Product entity
        product.setPrice(savedPrice);

        // Save the Product entity
        Product savedProduct = productRepo.save(product);

        // Convert and return the saved Product as DTO
        return DTOMapper.ProducttoGenericDtoMapper(savedProduct);
    }


//    @Override
//    public GenericProductDto getProductById(String id) {
//        Product Savedproduct = productRepo.findById(UUID.fromString(id)).get();
//        if(Savedproduct == null) {
//            return null;
//        }
//        return DTOMapper.ProducttoGenericDtoMapper(Savedproduct);
//    }


    public GenericProductDto getProductById(String id) throws NotFoundException {

            Optional<Product> OpsProduct = productRepo.findById(UUID.fromString(id));
        if (OpsProduct.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Product savedProduct = OpsProduct.get();
            return DTOMapper.ProducttoGenericDtoMapper(savedProduct);

    }


    @Override
    public GenericProductDto UpdateProductById(GenericProductDto genericProductDto, String id) throws NotFoundException {
        Optional<Product> Opsproduct = productRepo.findById(UUID.fromString(id));
        if (Opsproduct.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        Product product = Opsproduct.get();
        Optional<Category> OpsCategory = categoryRepo.findByName(genericProductDto.getCategory());
        if (OpsCategory.isEmpty()) {
            Category category = new Category();
            category.setName(genericProductDto.getCategory());
            Category savedCategory = categoryRepo.save(category);
            product.setCategory(savedCategory);

        }
        else {
            Category category = OpsCategory.get();
            product.setCategory(category);
        }

        Optional<Price> OpsPrice = priceRepo.findByName(genericProductDto.getPrice());
        if(OpsPrice.isEmpty()) {
            Price price = new Price();
            price.setName(genericProductDto.getPrice());
            Price savedPrice = priceRepo.save(price);
            product.setPrice(savedPrice);
        }
        else {
            Price price = OpsPrice.get();
            product.setPrice(price);
        }

        product.setDescription(genericProductDto.getDescription());
        product.setTitle(genericProductDto.getTitle());
        product.setImage(genericProductDto.getImage());


        Product savedProduct = productRepo.save(product);

        return DTOMapper.ProducttoGenericDtoMapper(product);


    }

    public GenericProductDto deletProductById(String id) throws NotFoundException {
        Optional<Product> opsProduct = productRepo.findById(UUID.fromString(id));
        if (opsProduct.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        productRepo.deleteById(UUID.fromString(id));
        Product product = opsProduct.get();
        return DTOMapper.ProducttoGenericDtoMapper(product);

    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> allProducts = productRepo.findAll();

        ArrayList<GenericProductDto> arr = new ArrayList<>();

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            Category category1 = product.getCategory();
            Price price = product.getPrice();
            if (category1 == null) {
                continue;
//                throw new CategoryNullException("There is no category for this product")
            }

            if(price == null) {
                continue;
            }
            Optional<Category> category = categoryRepo.findByName(product.getCategory().getName());
            Optional<Price> price1 = priceRepo.findByName(product.getPrice().getName());
            product.setCategory(category.get());
            product.setPrice(price1.get());
            arr.add(DTOMapper.ProducttoGenericDtoMapper(product));

        }
        return arr;
    }

    @Override
    public List<GenericProductDto> getAllProductsByCategoryName(String name) throws InvalidCategoryException {
//        Product product = new Product();
        Optional<Category> Opscategory = categoryRepo.findByName(name);
        if (Opscategory.isEmpty()) {
            throw new InvalidCategoryException("No category found with the given name");
        }
        List<Product> allProducts = productRepo.findAll();

        ArrayList<GenericProductDto> arr = new ArrayList<>();

        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            Category category1 = product.getCategory();
            if (category1 == Opscategory.get()) {
                product.setCategory(category1);
                arr.add(DTOMapper.ProducttoGenericDtoMapper(product));
            }
        }
//        return Opscategory.get().getProduct().stream().map(DTOMapper::ProducttoGenericDtoMapper).toList();
//        or
//        List<Product> allProducts = productRepo.findAll();
//List<GenericProductDto> arr = allProducts.stream()
//        .filter(product -> product.getCategory().equals(Opscategory.orElse(null)))
//        .map(DTOMapper::ProducttoGenericDtoMapper)
//        .collect(Collectors.toList());
            return arr;


    }

    @Override
    public List<String> getAllCategories() {
//        ArrayList<String> arr = new ArrayList<>();
//        List<Category> categories =  categoryRepo.findAll();
//
//        for(Category category: categories) {
//            arr.add(category.getName());
//        }
//
//        return arr;

        List<String> arr = categoryRepo.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        return arr;


    }
}
