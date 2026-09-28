package org.example.Entityes;

import lombok.Getter;

@Getter
public class Customer {
    private String fistName;
    private Long id;
    private String secondName;
    private int age;
    private String login;
    private Integer balance;

    public Customer(String firstName, String secondName, Long id, int age, String login, Integer balance ){
        this.age = age;
        this.fistName = firstName;
        this.secondName = secondName;
        this.id = id;
        this.login = login;
        this.balance = balance;
    }
}
