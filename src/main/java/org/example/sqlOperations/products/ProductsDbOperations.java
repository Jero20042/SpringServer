package org.example.sqlOperations.products;

import org.example.Entityes.Customer;
import org.example.Entityes.Product;
import org.example.sqlOperations.SqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDbOperations extends SqlConnection {

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sqlGetProducts = """
                select * from products
                """;
        ResultSet resultDbRequest = sendQuery(sqlGetProducts);
        while (resultDbRequest.next()) {
            Long productId = resultDbRequest.getLong("product_id");
            String name = resultDbRequest.getString("name");
            Integer price = resultDbRequest.getInt("price");
            Integer count = resultDbRequest.getInt("count");


            products.add(new Product(productId,
                    name,
                    price,
                    count));
        }
        return products;
    }

    public Product addNewProduct(String name, int price, int count) {
        String sqlAddNewProduct = """
                insert into products (name,price,count) values ('%s', %d, %d)
                """.formatted(name, price, count);
        try {
            sendUpdate(sqlAddNewProduct);
            Product newProduct = getProductByName(name);
            return newProduct;
        } catch (Exception e) {
            System.out.println("Продукт не добавлен");
            return null;
        }
    }

    public void removeProductById(Long id) {
        String sqlRemoveProductById = """
                delete from products where product_id = %d
                """.formatted(id);
        try {
            sendDelete(sqlRemoveProductById);
        } catch (Exception e) {
            System.out.println("Продукт не удален");
        }
    }

    public void removeProductByName(String name) {
        String sqlRemoveProductById = """
                delete from products where name = '%s'
                """.formatted(name);
        try {
            sendDelete(sqlRemoveProductById);
        } catch (Exception e) {
            System.out.println("Продукт не удален");
        }
    }

    public Product getProductByName(String name) throws SQLException {
        String sqlGetProductByName = """
                select * from products where name = '%s'
                """.formatted(name);
        try {
            ResultSet resultSet = sendQuery(sqlGetProductByName);
            if (resultSet.next()) {
                return new Product(resultSet.getLong("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getInt("count"));
            }
        } catch (Exception e) {
            System.out.println("Не удалось найти продукт по имени");
        }

        return null;
    }

    public Product getProductById(Long id) throws SQLException {
        String sqlGetProductByName = """
                select * from products where product_id = %d
                """.formatted(id);
        try {
            ResultSet resultSet = sendQuery(sqlGetProductByName);
            if (resultSet.next()) {
                return new Product(resultSet.getLong("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getInt("count"));
            }
        } catch (Exception e) {
            System.out.println("Не удалось найти продукт по имени");
        }
        return null;
    }

    public Product updateProduct(String name, String newName, int newPrice, int newCount){
        String sqlUpdate = """
                update products set name = '%s', price = %d , count = %d where name = '%s'
                """.formatted(newName,newPrice,newCount,name);
        try {
            sendUpdate(sqlUpdate);
            return getProductByName(newName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product updateProduct(Long id, String newName, int newPrice, int newCount){
        String sqlUpdate = """
                update products set name = '%s', price = %d , count = %d where product_id = %d
                """.formatted(newName,newPrice,newCount,id);
        try {
            sendUpdate(sqlUpdate);
            return getProductByName(newName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
