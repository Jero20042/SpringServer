package org.example.requests;

import org.example.sqlOperations.customer.CustomerOperation;
import org.example.sqlOperations.SessionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.Duration;

@RestController
public class LoginRequest {
    private final CustomerOperation customerOperation =
            new CustomerOperation();
    private final SessionService sessionService;

    public LoginRequest(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginRequest(@RequestBody RegisterRequest request) throws SQLException {
        boolean isLoginSuccess = customerOperation.loginCustomer(request.login(),request.password());
        if (isLoginSuccess){
            String token = sessionService.createSession(request.login());

            ResponseCookie cookie = ResponseCookie
                    .from("skey", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Strict")
                    .build();

            return ResponseEntity
                    .status(200)
                    .header(HttpHeaders.SET_COOKIE,cookie.toString())
                    .body("Login успешный");
        } else {
            return ResponseEntity
                    .status(400)
                    .body("Вход не выполнен");
        }
    }
}
