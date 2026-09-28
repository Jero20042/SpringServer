package org.example.responses.ProductsResponses;

import lombok.Getter;
import lombok.Setter;
import org.example.Entityes.Product;

@Getter
@Setter
public class ProductResponse{
    private Long productId;
    private String name;
    private int price;
    private int count;


    public ProductResponse(Product product) {
        this.productId = product.getProductId();
       this.name = product.getName();
       this.price = product.getPrice();
       this.count = product.getCount();
    }
}
