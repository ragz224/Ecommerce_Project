package com.raghu.productservice.ThirdpartyClients;

import com.raghu.productservice.DTOs.GenericProductDto;
import com.raghu.productservice.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreClient {


    @Value("${fakeStore.api.baseUrl}")
    private String fakeStoreApiBaseUrl;

    @Value("${fakestore.api.product}")
    private String fakeStoreProductPath;

    private final String productPath = "/products";


    private String productUrl = fakeStoreApiBaseUrl + productPath + "/{id}";
    private String getPostproductsUrl = fakeStoreApiBaseUrl + fakeStoreProductPath;
    private RestTemplate restTemplate;
    private RestTemplateBuilder restTemplateBuilder;



        @Autowired
        public FakeStoreClient(RestTemplateBuilder restTemplateBuilder,
                               @Value("${fakeStore.api.baseUrl}") String fakeStoreApiBaseUrl,
                               @Value("${fakestore.api.product}") String fakeStoreProductPath) {
            this.restTemplateBuilder = restTemplateBuilder;
            this.productUrl = fakeStoreApiBaseUrl + fakeStoreProductPath + "/{id}";
            this.getPostproductsUrl = fakeStoreApiBaseUrl + fakeStoreProductPath;
        }




        public GenericProductDto Insertingdata(FakeStoreProductDto fakeStoreProductDto) {
            GenericProductDto genericProductDto = new GenericProductDto();
            genericProductDto.setId(String.valueOf(fakeStoreProductDto.getId()));
            genericProductDto.setTitle(fakeStoreProductDto.getTitle());
//            genericProductDto.setCategory(fakeStoreProductDto.getCategory());
            genericProductDto.setDescription(fakeStoreProductDto.getDescription());
            genericProductDto.setImage(fakeStoreProductDto.getImage());
//            genericProductDto.setPrice(fakeStoreProductDto.getPrice());

            return genericProductDto;
        }



        public FakeStoreProductDto getProductById(Long id) throws NotFoundException {
            RestTemplate restTemplate = restTemplateBuilder.build();

             ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(productUrl,
                     FakeStoreProductDto.class, id);

            FakeStoreProductDto fakeStoreProductDto = response.getBody();

            if(fakeStoreProductDto == null) {
                throw new NotFoundException("product Id  " + id + " not found");
            }



            return fakeStoreProductDto;
        }



        public FakeStoreProductDto uploadProduct(GenericProductDto genericProductDto) {
            RestTemplate restTemplate = restTemplateBuilder.build();
            ResponseEntity<FakeStoreProductDto> response =
                    restTemplate.postForEntity(getPostproductsUrl,genericProductDto,FakeStoreProductDto.class);

            FakeStoreProductDto fakeStoreProductDto = response.getBody();

            return fakeStoreProductDto;

        }




        public FakeStoreProductDto updateProductById(Long id, GenericProductDto updatedProductDto) {

            RestTemplate restTemplate = restTemplateBuilder.build();
            HttpEntity<GenericProductDto> requestEntity = new HttpEntity<>(updatedProductDto);

            ResponseEntity<FakeStoreProductDto> response =
                    restTemplate.exchange(
                            productUrl,
                            HttpMethod.PUT, requestEntity,FakeStoreProductDto.class,
                            id
                    );
            FakeStoreProductDto fakeStoreProductDto = response.getBody();


            return fakeStoreProductDto;


        }



        public List<FakeStoreProductDto> getAllProducts() {
            RestTemplate restTemplate = restTemplateBuilder.build();

            ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(getPostproductsUrl, FakeStoreProductDto[].class);


            FakeStoreProductDto[] fakeStoreProductDtoArray = response.getBody();




            return Arrays.asList(fakeStoreProductDtoArray);

        }


        public FakeStoreProductDto DeleteProductById(Long id) {
            RestTemplate restTemplate = restTemplateBuilder.build();

            ResponseEntity<FakeStoreProductDto> response =  restTemplate.exchange(productUrl, HttpMethod.DELETE,null, FakeStoreProductDto.class,id);

            FakeStoreProductDto fakeStoreProductDto = response.getBody();



            return fakeStoreProductDto;


        }

}
