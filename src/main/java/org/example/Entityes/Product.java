package org.example.Entityes;

import lombok.Getter;

@Getter
public class Product {
    private Long productId;
    private String name;
    private Integer price;
    private Integer count;

    public Product(Long productId,String name, Integer price, Integer count){
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Product(String name, Integer price, Integer count){
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
