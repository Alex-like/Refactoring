package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryTest extends BaseAddTest {

    public QueryTest() {
        super(new QueryServlet(), "get-products");
    }

    private void query(String query, List<String> expected) throws ServletException, IOException {
        ArrayList<String> expectedRes = new ArrayList<>();
        expectedRes.add("<html><body>");
        expectedRes.addAll(expected);
        expectedRes.add("</body></html>" + System.lineSeparator());
        servletAssertCall(
                Map.of("command", query),
                String.join(System.lineSeparator(), expectedRes)
        );
    }

    private void count(Integer expectedCount) throws ServletException, IOException {
        query("count", List.of("Number of products: ", expectedCount.toString()));
    }

    private void sum(Integer expectedSum) throws ServletException, IOException {
        query("sum", List.of("Summary price: ", expectedSum.toString()));
    }

    private void min(String expectedProduct) throws ServletException, IOException {
        ArrayList<String> expected = new ArrayList<>(List.of("<h1>Product with min price: </h1>"));
        if (!expectedProduct.isEmpty())
            expected.add(expectedProduct + "</br>");
        query("min", expected);
    }

    private void max(String expectedProduct) throws ServletException, IOException {
        ArrayList<String> expected = new ArrayList<>(List.of("<h1>Product with max price: </h1>"));
        if (!expectedProduct.isEmpty())
            expected.add(expectedProduct + "</br>");
        query("max", expected);
    }

    @Test
    public void unknownCommand() throws ServletException, IOException {
        String command = "Let me see you";
        servletAssertCall(
                Map.of("command", command),
                "Unknown command: " + command + System.lineSeparator()
        );
    }

    @Test
    public void emptyCount() throws ServletException, IOException {
        count(0);
    }

    @Test
    public void emptySum() throws ServletException, IOException {
        sum(0);
    }

    @Test
    public void emptyMin() throws ServletException, IOException {
        min("");
    }

    @Test
    public void emptyMax() throws ServletException, IOException {
        max("");
    }

    @Test
    public void twoProductsCount() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 7", 400);
        count(2);
    }

    @Test
    public void twoProductsSum() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 7", 400);
        sum(700);
    }

    @Test
    public void twoProductsMin() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 7", 400);
        min("iphone 6\t" + 300);
    }

    @Test
    public void twoProductsMax() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 7", 400);
        max("iphone 7\t" + 400);
    }
}
