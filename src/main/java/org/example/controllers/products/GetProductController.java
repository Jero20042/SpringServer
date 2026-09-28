package org.example.controllers.products;

import org.example.Entityes.Product;
import org.example.responses.ProductsResponses.ProductResponse;
import org.example.sqlOperations.products.ProductsDbOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;

@RestController
public class GetProductController {
    private final ProductsDbOperations productsDbOperations =
            new ProductsDbOperations();

    @GetMapping(value = "/product", params = "name")
    public ResponseEntity<?> getProductByName(@RequestParam("name") String name) throws SQLException {
    Product product = productsDbOperations.getProductByName(name);
        if (product != null) {
            ProductResponse productResponse = new ProductResponse(product);
            return ResponseEntity
                    .status(200)
                    .body(productResponse);
        }
        else {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message","Продукт не найден"));
        }
    }

    @GetMapping(value = "/product", params = "id")
    public ResponseEntity<?> getProductById(@RequestParam("id") Long id) throws SQLException {
        Product product = productsDbOperations.getProductById(id);
        if (product != null) {
            ProductResponse productResponse = new ProductResponse(product);
            return ResponseEntity
                    .status(200)
                    .body(productResponse);
        }
        else {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message","Продукт не найден"));
        }
    }
}
