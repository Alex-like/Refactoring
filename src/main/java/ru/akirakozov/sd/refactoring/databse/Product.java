package ru.akirakozov.sd.refactoring.databse;

public class Product {
    private String  name;
    private Long    price;

    public Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String toString() {
        return name + "\t" + price + "</br>";
    }
}
