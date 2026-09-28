package org.example.responses.CustomersResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessGetInfoResponse {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private int age;
    private Integer balance;
}
