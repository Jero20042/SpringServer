package org.example;

import org.example.Entityes.Customer;
import org.example.Entityes.Order;
import org.example.Entityes.Product;
import org.example.sqlOperations.customer.CustomerOperation;
import org.example.sqlOperations.orders.OrdersDbOperations;
import org.example.sqlOperations.products.ProductsDbOperations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws SQLException {
        CustomerOperation customerOperation = new CustomerOperation();
        ProductsDbOperations productsDbOperations = new ProductsDbOperations();
        OrdersDbOperations ordersDbOperations = new OrdersDbOperations();

        String s = "";
    }
}
