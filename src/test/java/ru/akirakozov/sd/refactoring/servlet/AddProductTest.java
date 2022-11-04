package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.databse.DummyDataBase;

import java.io.IOException;

public class AddProductTest extends BaseAddTest {
    public AddProductTest() {
        super(AbstractServlet::new, new DummyDataBase());
    }

    @Test
    public void addOneProduct() throws ServletException, IOException {
        addProduct("iphone 6", 300);
    }

    @Test
    public void addTwoProducts() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 7", 400);
    }

    @Test
    public void addTwoProductsWithSameNames() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 6", 200);
    }
}
