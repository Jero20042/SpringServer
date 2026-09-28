package org.example.controllers.products;

import org.example.Entityes.Product;
import org.example.requests.ProductRequests.CreateProductRequest;
import org.example.sqlOperations.products.ProductsDbOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CreateProductController {
    private final ProductsDbOperations productsDbOperations = new ProductsDbOperations();
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest request){
    try {
       Product checkProduct = productsDbOperations.getProductByName(request.name());
       if (checkProduct != null) {
           return ResponseEntity
                   .status(400)
                   .body(Map.of("message", "Продукт с таким именем уже существует"));
       }

       Product product = productsDbOperations.addNewProduct(request.name(),
                request.price(),
                request.count());
       if (product != null) {
           return ResponseEntity
                   .status(201)
                   .body(product);
       } else {
           return ResponseEntity
                   .status(400)
                   .body(Map.of("message", "Продукт не создан"));
       }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }
}
