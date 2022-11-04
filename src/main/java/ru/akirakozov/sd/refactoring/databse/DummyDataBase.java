package ru.akirakozov.sd.refactoring.databse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DummyDataBase implements DataBase {
    private final List<Product> productList = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        productList.add(product);
    }

    @Override
    public List<Product> selectAllProducts() {
        return new ArrayList<>(productList);
    }

    @Override
    public int getProductsCount() {
        return productList.size();
    }

    @Override
    public long getProductPricesSum() {
        return productList.stream().map(Product::getPrice).reduce(0L, Long::sum);
    }

    @Override
    public Optional<Product> getProductWithMinPrice() {
        return productList.stream().min(Comparator.comparingLong(Product::getPrice));
    }

    @Override
    public Optional<Product> getProductWithMaxPrice() {
        return productList.stream().max(Comparator.comparingLong(Product::getPrice));
    }

    @Override
    public void dropTable() {
        productList.removeAll(selectAllProducts());
    }
}
