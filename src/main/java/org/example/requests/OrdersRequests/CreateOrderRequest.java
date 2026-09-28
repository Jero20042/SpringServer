package org.example.requests.OrdersRequests;

public record CreateOrderRequest(Long customerId, Long productId, Integer count) {
}
