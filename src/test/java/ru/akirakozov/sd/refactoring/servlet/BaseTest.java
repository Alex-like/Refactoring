package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.databse.DataBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class BaseTest extends Assert {
    private final HttpServlet servlet;
    private final DataBase dataBase;

    @FunctionalInterface
    protected interface ServletProducer<T> {
        T apply(DataBase db);
    }

    public BaseTest(ServletProducer<HttpServlet> servletProducer, DataBase dataBase) {
        this.servlet = servletProducer.apply(dataBase);
        this.dataBase = dataBase;
    }

    @After
    public void dropTable() {
        dataBase.dropTable();
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
