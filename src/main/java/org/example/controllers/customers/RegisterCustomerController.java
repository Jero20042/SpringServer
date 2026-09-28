package org.example.controllers.customers;

import org.example.sqlOperations.customer.CustomerOperation;
import org.example.requests.RegisterRequest;
import org.example.responses.CustomersResponses.SuccessRegisterResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
public class RegisterCustomerController {
    private final CustomerOperation customerOperation =
            new CustomerOperation();

    @PostMapping("/register")
    public ResponseEntity<SuccessRegisterResponse> register(@RequestBody RegisterRequest request) throws SQLException {
        boolean registered = customerOperation.registrationCustomer(
                request.name(),
                request.lastName(),
                request.age(),
                request.login(),
                request.password()
        );

        if (!registered){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new SuccessRegisterResponse(null,null,0,null,"error"));
        }

        SuccessRegisterResponse successResponse =
                new SuccessRegisterResponse(
                        request.name(),
                        request.lastName(),
                        request.age(),
                        request.login(),
                        "Пользователь создан успешно."
                );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(successResponse);
    }
}
