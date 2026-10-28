package s1.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileStorage implements InvoiceStorage {
    public static final List<String> FILES = new ArrayList<>();

    @Override
    public void save(Invoice invoice, double total) {
        FILES.add(invoice.getNumber() + ";" + invoice.getCustomerEmail() + ";"
                + String.format(Locale.US, "%.2f", total));
    }
}