package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.databse.DataBase;
import ru.akirakozov.sd.refactoring.databse.Product;

import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    public AddProductServlet(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        dataBase.addProduct(new Product(name, price));

        response.getWriter().println("OK");
        setResponse(response);
    }
}
