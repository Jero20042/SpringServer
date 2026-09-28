package org.example.controllers.orders;


import org.example.Entityes.Order;
import org.example.requests.OrdersRequests.CreateOrderRequest;
import org.example.sqlOperations.SessionService;
import org.example.sqlOperations.customer.CustomerOperation;
import org.example.sqlOperations.orders.OrdersDbOperations;
import org.example.sqlOperations.products.ProductsDbOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CreateOrderConroller {
    private final CustomerOperation customerOperation = new CustomerOperation();
    private final ProductsDbOperations productsDbOperations = new ProductsDbOperations();
    private final OrdersDbOperations ordersDbOperations = new OrdersDbOperations();
    private final SessionService sessionService;
    public CreateOrderConroller(SessionService sessionService) {this.sessionService = sessionService;}
    @PostMapping("orders/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request, @CookieValue(value = "skey", required = false) String token){
        try {
            String login = customerOperation.getCustomerById(request.customerId()).getLogin();
            if (sessionService.getLoginByToken(token).get().equals(login)) {
                return ResponseEntity
                        .status(200)
                        .body(ordersDbOperations.createNewOrder(
                                customerOperation.getCustomerById(request.customerId()),
                                productsDbOperations.getProductById(request.productId()),
                                request.count()));
            } else {
                return ResponseEntity
                        .status(401)
                        .body(Map.of("message", "Нельзя создать заказ для другого пользователя"));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("message", "Заказ не создан"));
        }
    }
    @GetMapping("/orders/myOrders")
    public ResponseEntity<?> getMyOrders(@CookieValue(value = "skey", required = false) String token){
        try {
            List<Order> ordersList = ordersDbOperations.getAllOrdersByLogin(sessionService.getLoginByToken(token).get());
           if (!ordersList.isEmpty()) {
               return ResponseEntity
                       .status(200)
                       .body(ordersList);
           } else {
               return ResponseEntity
                       .status(200)
                       .body(Map.of("message", "Заказы не найдены"));

           }
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("message", "неизвестная ошибка"));
        }
    }
}
