package org.example.responses.CustomersResponses;

public record SuccessRegisterResponse(
        String name,
        String lastName,
        int age,
        String login,
        String message
) {


}
