package s1.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseStorage implements InvoiceStorage {
    public static final List<String> DATABASE = new ArrayList<>();

    @Override
    public void save(Invoice invoice, double total) {
        DATABASE.add(invoice.getNumber() + ";" + invoice.getCustomerEmail() + ";"
                + String.format(Locale.US, "%.2f", total));
    }
}