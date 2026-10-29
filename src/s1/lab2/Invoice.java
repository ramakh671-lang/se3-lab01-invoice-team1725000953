package s1.lab2;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private final int number;
    private final String customerEmail;
    private final List<InvoiceItem> items = new ArrayList<>();

    public Invoice(int number, String customerEmail) {
        this.number = number;
        this.customerEmail = customerEmail;
    }

    public void addItem(String name, double price, int qty) {
        items.add(new InvoiceItem(name, price, qty));
    }

    public List<InvoiceItem> getItems() { return items; }
    public int getNumber() { return number; }
    public String getCustomerEmail() { return customerEmail; }
}