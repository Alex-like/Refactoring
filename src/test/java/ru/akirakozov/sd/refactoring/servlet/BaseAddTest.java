package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.Map;

public class BaseAddTest extends BaseTest {
    static class AddServletCall extends BaseTest {
        public AddServletCall() {
            super(new AddProductServlet(), "add-product");
        }
    }

    private final AddServletCall addProductServlet = new AddServletCall();

    public BaseAddTest(HttpServlet servlet, String endPath) {
        super(servlet, endPath);
    }

    public void addProduct(String name, Integer price) throws ServletException, IOException {
        addProductServlet.servletAssertCall(
                Map.of("name", name, "price", price.toString()),
                "OK" + System.lineSeparator()
        );

    }
}
