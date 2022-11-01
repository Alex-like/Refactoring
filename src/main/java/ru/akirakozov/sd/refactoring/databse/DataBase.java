package ru.akirakozov.sd.refactoring.databse;

import java.util.List;

public interface DataBase {
    public void             addProduct(Product product);
    public List<Product>    selectAllProducts();
    public Integer          getProductsCount();
    public Long             getProductPricesSum();
    public Product          getProductWithMinPrice();
    public Product          getProductWithMaxPrice();
}
