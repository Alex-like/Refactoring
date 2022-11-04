package ru.akirakozov.sd.refactoring.servlet;

import jakarta.servlet.ServletException;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.databse.DummyDataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetProductTest extends BaseAddTest {
    public GetProductTest() {
        super(GetProductsServlet::new, new DummyDataBase());
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
        addProduct("iphone 6", 300);
        getProduct(List.of("iphone 6\t" + 300));
    }

    @Test
    public void getTwoProducts() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 7", 400);
        getProduct(List.of("iphone 6\t" + 300, "iphone 7\t" + 400));
    }

    @Test
    public void getTwoWithSameNamesProducts() throws ServletException, IOException {
        addProduct("iphone 6", 300);
        addProduct("iphone 6", 200);
        getProduct(List.of("iphone 6\t" + 300, "iphone 6\t" + 200));
    }
}
