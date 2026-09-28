package org.example.controllers.customers;

import org.example.sqlOperations.customer.CustomerOperation;
import org.example.sqlOperations.SessionService;
import org.example.responses.CustomersResponses.SuccessGetInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class GetCustomerInfoController {
    private final CustomerOperation customerOperation =
            new CustomerOperation();
    private final SessionService sessionService;
    public GetCustomerInfoController(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    @GetMapping("/info")
    public ResponseEntity<SuccessGetInfoResponse> getInfo(@CookieValue(value = "skey", required = false) String token) throws SQLException {
        return ResponseEntity
                .status(200)
                .body(customerOperation.getCustomerInfo(sessionService
                        .getLoginByToken(token)
                        .orElse(null)));
    }
}
