package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.databse.DataBase;

import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    public GetProductsServlet(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Formatter formatter = new Formatter();

        dataBase.selectAllProducts().forEach(formatter::add);
        formatter.write(response.getWriter());

        setResponse(response);
    }
}
