package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import org.junit.Test;

import java.io.IOException;

public class AddProductTest extends BaseAddTest {
    public AddProductTest() {
        super(new AddProductServlet(), "add-product");
    }

    @Test
    public void addOneProduct() throws ServletException, IOException {
        addProduct("iphone 6", 300L);
    }

    @Test
    public void addTwoProducts() throws ServletException, IOException {
        addProduct("iphone 6", 300L);
        addProduct("iphone 7", 400L);
    }

    @Test
    public void addTwoProductsWithSameNames() throws ServletException, IOException {
        addProduct("iphone 6", 300L);
        addProduct("iphone 6", 200L);
    }
}
