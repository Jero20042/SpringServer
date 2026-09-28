package org.example.requests;

public record RegisterRequest(
        String name,
        String lastName,
        int age,
        String login,
        String password
) {
}