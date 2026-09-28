package org.example.controllers.customers;

import org.example.Entityes.Customer;
import org.example.requests.UpdateCustomerBanalceRequest;
import org.example.sqlOperations.customer.CustomerOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;

@RestController
public class UpdateCustomerBalanceController {
    private final CustomerOperation customerOperation = new CustomerOperation();
    @PostMapping("updateBalance")
    public ResponseEntity<?> updateBalance(@RequestBody UpdateCustomerBanalceRequest requestBody) throws SQLException {
        try {
            Customer customer = getCustomerByLogin(requestBody.login());
            if (customer.getBalance() + requestBody.amount() >= 0) {
                customerOperation.updateCustomerBalance(customer, requestBody.amount());
                System.out.println("Баланс пользователя обновлен");
                return ResponseEntity.status(200).body(getCustomerByLogin(requestBody.login()));
            } else {
                return ResponseEntity
                        .status(400)
                        .body(Map.of("message","Баланс не может быть отрицательным"));
            }
        } catch (Exception e) {
            System.out.println("Баланс не обновлен");
        }
        return ResponseEntity
                .status(400)
                .body(Map.of("message", "error"));
    }

    private Customer getCustomerByLogin(String login) throws SQLException {
        try {
            return customerOperation
                    .getCustomers()
                    .stream()
                    .filter(
                            r -> r.getLogin().equals(login))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.out.println("Пользователь не найден");
        }
        return null;
    }
}
