package org.example.requests.ProductRequests;



public record CreateProductRequest(
        String name,
        int price,
        int count
) {
}
