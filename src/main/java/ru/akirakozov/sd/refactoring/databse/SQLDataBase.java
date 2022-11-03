package ru.akirakozov.sd.refactoring.databse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SQLDataBase implements DataBase {
    private final Connection connectionToDataBase;

    public SQLDataBase(Connection connectionToDataBase) {
        this.connectionToDataBase = connectionToDataBase;
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        update(sql);
    }

    private interface SQLFunction<T> {
        void apply(T arg) throws SQLException;
    }

    private void SQLExecutor(SQLFunction<Statement> executor) {
        try (Statement stmt = connectionToDataBase.createStatement()) {
            executor.apply(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(String sql) {
        SQLExecutor(stmt -> stmt.executeUpdate(sql));
    }

    private void executeWithResult(String sql, SQLFunction<ResultSet> executor) {
        SQLExecutor(stmt -> {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    executor.apply(rs);
                }
        });
    }

    private Integer integerQuery(String sql) {
        AtomicInteger result = new AtomicInteger();
        executeWithResult(sql, res -> result.set((res.getInt(1))));
        return result.get();
    }

    private List<Product> productsQuery(String sql) {
        ArrayList<Product> result = new ArrayList<>();
        executeWithResult(sql, res -> result.add(new Product(
                res.getString("name"),
                res.getInt("price")))
        );
        return result;
    }

    @Override
    public void addProduct(Product product) {
        update("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    @Override
    public List<Product> selectAllProducts() {
        return productsQuery("SELECT * FROM PRODUCT");
    }

    @Override
    public Integer getProductsCount() {
        return integerQuery("SELECT COUNT(*) FROM PRODUCT");
    }

    @Override
    public Integer getProductPricesSum() {
        return integerQuery("SELECT SUM(price) FROM PRODUCT");
    }

    @Override
    public Product getProductWithMinPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        List<Product> maxProduct = productsQuery(sql);
        return maxProduct.get(0);
    }

    @Override
    public Product getProductWithMaxPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        List<Product> maxProduct = productsQuery(sql);
        return maxProduct.get(0);
    }
}
