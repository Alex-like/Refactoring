package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class BaseTest extends Assert {
    private final HttpServlet servlet;
    private final String endPath;

    public BaseTest(HttpServlet servlet, String endPath) {
        this.servlet = servlet;
        this.endPath = endPath;
    }

    void SQL(String cmd) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = c.createStatement();
            statement.executeUpdate(cmd);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Before
    public void createTable() {
        SQL("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    @After
    public void dropTable() {
        SQL("DROP TABLE PRODUCT");
    }

    public void servletAssertCall(Map<String, String> req, String res)
            throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        for (Map.Entry<String, String> entry : req.entrySet()) {
            Mockito.when(request.getParameter(entry.getKey())).thenReturn(entry.getValue());
        }

        servlet.service(request, response);

        Mockito.verify(request, Mockito.times(req.size()))
                .getParameter(Mockito.any());
        Mockito.verify(response, Mockito.times(1))
                .setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(response, Mockito.times(1))
                .setContentType("text/html");
        Mockito.verify(response, Mockito.atLeastOnce()).getWriter();

        assertEquals(writer.toString(), res);
    }
}
