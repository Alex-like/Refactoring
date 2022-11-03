package ru.akirakozov.sd.refactoring.databse;

import java.util.List;
import java.util.Optional;

public interface DataBase {
    public void                 addProduct(Product product);
    public List<Product>        selectAllProducts();
    public Integer              getProductsCount();
    public Integer              getProductPricesSum();
    public Optional<Product>    getProductWithMinPrice();
    public Optional<Product>    getProductWithMaxPrice();
    public void                 dropTable();
}
