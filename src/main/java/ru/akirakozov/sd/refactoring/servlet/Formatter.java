package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.databse.Product;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Formatter {
    private final ArrayList<Object> lines = new ArrayList<>();

    public void add(Object line) {
        lines.add(line);
    }

    public void add(Product p) {
        lines.add(p.toString());
    }

    public void write(PrintWriter writer) {
        writer.println("<html><body>");
        lines.forEach(writer::println);
        writer.println("</body></html>");
    }
}
