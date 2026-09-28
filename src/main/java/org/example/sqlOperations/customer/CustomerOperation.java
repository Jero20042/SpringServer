package org.example.sqlOperations.customer;

import org.example.Entityes.Customer;
import org.example.responses.CustomersResponses.SuccessGetInfoResponse;
import org.example.sqlOperations.SqlConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerOperation extends SqlConnection {

    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customersFromQuery = new ArrayList<>();
        String selectCustomers = """
            SELECT * FROM customers
            """;
        ResultSet result = sendQuery(selectCustomers);
            while (result.next()) {
                Long id = Long.valueOf(result.getInt("id"));
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                int age = result.getInt("age");
                String login = result.getString("login");
                Integer balance = result.getInt("balance");

                customersFromQuery.add(new Customer(firstName,
                        lastName,
                        id,
                        age,
                        login,
                        balance));
            }
            return customersFromQuery;
        }

    public boolean registrationCustomer(String name, String lastName, int age, String login, String password) throws SQLException {
        boolean isRegistered = false;
        String selectCustomers = """
            SELECT * FROM customers where login = '%s'
            """.formatted(login);
        ResultSet result = sendQuery(selectCustomers);

            if (result.next()) {
                isRegistered = true;
                System.out.println("Пользователь с ником " + login + " уже зарегистрирован");
                return false;

            }

        if (!isRegistered) {
            String passwordHash = BCrypt.hashpw(
                    password,
                    BCrypt.gensalt()
            );
            String createUserQuery = """
                    insert into customers (first_name, last_name, age, login, pswd) 
                    VALUES ('%s' , '%s' , %d, '%s', '%s')
                    """.formatted(name, lastName, age, login, passwordHash);
            sendUpdate(createUserQuery);
            return true;
        }
        return false;
    }

    public boolean loginCustomer(String login, String password) throws SQLException {
        String sqlSearchCustomerInDb = """
                SELECT * FROM customers where login = '%s'
                           
                """.formatted(login);

        ResultSet resultSet = sendQuery(sqlSearchCustomerInDb);
        if (!resultSet.next()){
            System.out.println("Пользователь с таким именем не найден");
            return false;
        } else {
            String passwordFromDb = resultSet.getString("pswd");
            boolean passwordIsCorrect = BCrypt.checkpw(
                    password,
                    passwordFromDb
            );
            if (passwordIsCorrect){
                System.out.println("Авторизация успешно пройдена");
                return  true;
            } else {
                System.out.println("Пароль неверный");
                return  false;
            }

        }
    }

    public void clearCustomersDEBUG() throws SQLException {
        sendDelete("delete from customers");
    }

    public SuccessGetInfoResponse getCustomerInfo(String login) throws SQLException {
        String sqlGetInfo = """
                select * from customers where login = '%s'
                """.formatted(login);
        ResultSet resultSet = sendQuery(sqlGetInfo);
        if (!resultSet.next()) {
            return null;
        }
       SuccessGetInfoResponse getInfoSuccess = new SuccessGetInfoResponse();
        getInfoSuccess.setId(resultSet.getLong("id"));
        getInfoSuccess.setFirstName(resultSet.getString("first_name"));
        getInfoSuccess.setLastName(resultSet.getString("last_name"));
        getInfoSuccess.setLogin(resultSet.getString("login"));
        getInfoSuccess.setAge(resultSet.getInt("age"));
        getInfoSuccess.setBalance(resultSet.getInt("balance"));

        return getInfoSuccess;
    }
    public void updateCustomerBalance(Customer customer, Integer amount) {
        Integer finishBalance = customer.getBalance() + amount;
        if (finishBalance >= 0) {
            String updateBalance = """
                    update customers set balance = %d where id = %d
                    """.formatted(finishBalance, customer.getId());
            try {
                sendUpdate(updateBalance);
                System.out.println("Баланс изменен, новый баланс пользователя " + customer.getLogin() + " - " + finishBalance + "Рублей.");
            } catch (Exception e) {
                System.out.println("Баланс не был изменен");
            }
        } else {
            System.out.println("Баланс не может быть отрицательным");
        }
    }

    public Customer getCustomerById(Long id){
        String sqlGetCustomerByLogin = """
                select * from customers where id = %d
                """.formatted(id);
        try {
            ResultSet result = sendQuery(sqlGetCustomerByLogin);
            if (result.next()){
                System.out.println("Пользователь найден по id: "+id);
                return new Customer(
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getLong("id"),
                        result.getInt("age"),
                        result.getString("login"),
                        result.getInt("balance"));

            }
        } catch (Exception e) {
            System.out.println("Пользователь не найден по id");
            return null;
        }
        return null;
    }
    }

