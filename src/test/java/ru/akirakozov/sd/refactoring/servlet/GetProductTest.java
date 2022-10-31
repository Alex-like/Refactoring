package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetProductTest extends BaseAddTest {
    public GetProductTest() {
        super(new GetProductsServlet(), "get-products");
    }

    private void getProduct(List<String> expectedProducts) throws ServletException, IOException {
        ArrayList<String> expectedRes = new ArrayList<>();
        expectedRes.add("<html><body>");
        expectedRes.addAll(expectedProducts.stream().map(a -> a + "</br>").collect(Collectors.toList()));
        expectedRes.add("</body></html>" + System.lineSeparator());
        servletAssertCall(Collections.emptyMap(), String.join(System.lineSeparator(), expectedRes));
    }

    @Test
    public void getEmpty() throws ServletException, IOException {
        getProduct(Collections.emptyList());
    }

    @Test
    public void getOneProduct() throws ServletException, IOException {
        addProduct("iphone 6", 300L);
        getProduct(List.of("iphone 6\t" + 300L));
    }

    @Test
    public void getTwoProducts() throws ServletException, IOException {
        addProduct("iphone 6", 300L);
        addProduct("iphone 7", 400L);
        getProduct(List.of("iphone 6\t" + 300L, "iphone 7\t" + 400L));
    }

    @Test
    public void getTwoWithSameNamesProducts() throws ServletException, IOException {
        addProduct("iphone 6", 300L);
        addProduct("iphone 6", 200L);
        getProduct(List.of("iphone 6\t" + 300L, "iphone 6\t" + 200L));
    }
}
