package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.databse.DataBase;

public class AbstractServlet extends HttpServlet {
    protected final DataBase dataBase;

    public AbstractServlet(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    protected void setResponse(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
