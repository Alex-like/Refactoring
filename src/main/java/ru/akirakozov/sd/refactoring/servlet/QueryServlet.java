package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.databse.DataBase;

import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {
    public QueryServlet(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        Formatter formatter = new Formatter();

        switch (command) {
            case "max":
                formatter.add("<h1>Product with max price: </h1>");
                dataBase.getProductWithMaxPrice().ifPresent(formatter::add);
                break;
            case "min":
                formatter.add("<h1>Product with min price: </h1>");
                dataBase.getProductWithMinPrice().ifPresent(formatter::add);
                break;
            case "sum":
                formatter.add("Summary price: ");
                long sum = dataBase.getProductPricesSum();
                formatter.add(sum);
                break;
            case "count":
                formatter.add("Number of products: ");
                int count = dataBase.getProductsCount();
                formatter.add(count);
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
                setResponse(response);
                return;
        }

        formatter.write(response.getWriter());
        setResponse(response);
    }

}
