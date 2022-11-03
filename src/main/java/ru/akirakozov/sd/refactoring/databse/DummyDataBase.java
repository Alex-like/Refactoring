package ru.akirakozov.sd.refactoring.databse;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DummyDataBase implements DataBase {
    private final Multiset<Product> productList = HashMultiset.create();

    @Override
    public void addProduct(Product product) {
        productList.add(product);
    }

    @Override
    public List<Product> selectAllProducts() {
        return new ArrayList<>(productList);
    }

    @Override
    public Integer getProductsCount() {
        return productList.size();
    }

    @Override
    public Integer getProductPricesSum() {
        return productList.stream().map(Product::getPrice).reduce(0, Integer::sum);
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
