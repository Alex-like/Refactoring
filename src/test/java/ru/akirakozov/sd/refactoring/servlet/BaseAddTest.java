package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import ru.akirakozov.sd.refactoring.databse.DataBase;

import java.io.IOException;
import java.util.Map;

public class BaseAddTest extends BaseTest {
    static class AddServletCall extends BaseTest {
        public AddServletCall(DataBase dataBase) {
            super(AddProductServlet::new, dataBase);
        }
    }

    private final AddServletCall addProductServlet;

    public BaseAddTest(ServletProducer<HttpServlet> servletProducer, DataBase dataBase) {
        super(servletProducer, dataBase);
        this.addProductServlet = new AddServletCall(dataBase);
    }

    public void addProduct(String name, Integer price) throws ServletException, IOException {
        addProductServlet.servletAssertCall(
                Map.of("name", name, "price", price.toString()),
                "OK" + System.lineSeparator()
        );
    }
}
