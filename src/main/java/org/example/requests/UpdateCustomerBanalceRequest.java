package org.example.requests;



public record UpdateCustomerBanalceRequest(
        String login,
        Integer amount
) {
}
