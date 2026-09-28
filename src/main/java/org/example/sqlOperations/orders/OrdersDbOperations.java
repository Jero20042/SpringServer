package org.example.sqlOperations.orders;

import org.example.Entityes.Customer;
import org.example.Entityes.Order;
import org.example.Entityes.Product;
import org.example.sqlOperations.SqlConnection;
import org.example.sqlOperations.customer.CustomerOperation;
import org.example.sqlOperations.products.ProductsDbOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class OrdersDbOperations extends SqlConnection {
    public List<Order> getAllOrders(){
        List<Order> ordersFromDb = new ArrayList<>();
        try {
            String sqlGetAllOrders = """
                    select * from orders
                    """;
            ResultSet resultSet = sendQuery(sqlGetAllOrders);
            while (resultSet.next()){
                ordersFromDb.add(new Order(
                        resultSet.getLong("order_id"),
                        resultSet.getLong("customer_id"),
                        resultSet.getLong("product_id"),
                        resultSet.getInt("amount"),
                        resultSet.getString("order_name"),
                        resultSet.getTimestamp("date"))
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Order> getAllOrdersByLogin(String customerLogin){
        List<Order> ordersFromDb = new ArrayList<>();
        try {
            String sqlGetAllOrders = """
                    select * from orders where customer_id = (select id as customer_id from customers where login = '%s')
                    """.formatted(customerLogin);
            System.out.println(sqlGetAllOrders);
            ResultSet resultSet = sendQuery(sqlGetAllOrders);
            while (resultSet.next()){
                ordersFromDb.add(new Order(
                        resultSet.getLong("order_id"),
                        resultSet.getLong("customer_id"),
                        resultSet.getLong("product_id"),
                        resultSet.getInt("amount"),
                        resultSet.getString("product_name"),
                        resultSet.getTimestamp("date"))
                );
            }
        } catch (Exception e) {
            System.out.println("Заказы не найдены");
            return ordersFromDb;
        }
        return ordersFromDb;
    }

    private Order getOrderByDate(Timestamp date){
        try {
            String sqlGetOrderByDate = """
            SELECT * FROM orders
            WHERE date = '%s'
            """.formatted(date);
            ResultSet resultSet = sendQuery(sqlGetOrderByDate);
            if (resultSet.next()) {
                return new Order(
                        resultSet.getLong("order_id"),
                        resultSet.getLong("customer_id"),
                        resultSet.getLong("product_id"),
                        resultSet.getInt("amount"),
                        resultSet.getString("product_name"),
                        resultSet.getTimestamp("date"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Order createNewOrder(Customer customer, Product product, Integer orderCount ){
        int productPrice = product.getPrice();
        Integer totalAmount = productPrice * orderCount;
        Timestamp orderDate = Timestamp.valueOf(LocalDateTime.now());
        String sqlCreateNewOrder = """
                insert into orders (customer_id, product_id, amount, date, product_name) values (%d,%d,%d,'%s', (select name as product_name from products where product_id = %d))
                """.formatted(customer.getId(),
                product.getProductId(), totalAmount, orderDate, product.getProductId());


        try {

            Integer customerBalance = new CustomerOperation().getCustomerInfo(customer.getLogin()).getBalance();
            int productCount = new ProductsDbOperations().getProductByName(product.getName()).getCount();
                    if (customerBalance >= totalAmount && productCount > 0){
                        //create new order
                        sendUpdate(sqlCreateNewOrder);
                        Integer newCount = productCount -orderCount;
                        //update count for product
                        new ProductsDbOperations().updateProduct(product.getName(),
                                product.getName(),
                                product.getPrice(),
                                newCount);
                        //update balance for customer
                        new CustomerOperation().updateCustomerBalance(customer,-totalAmount);
                        return getOrderByDate(orderDate);
                    } else {
                        System.out.println("Заказ не создан!\nКоличество товаров на складе: " + productCount+ "\nБаланс пользователя: " + customerBalance);
                        return null;
                    }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
