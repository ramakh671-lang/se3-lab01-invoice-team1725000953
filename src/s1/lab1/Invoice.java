package s1.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Invoice {

    int number;
    String customerEmail;
    List<InvoiceItem> items;

    static List<String> DATABASE = new ArrayList<>();
    static List<String> FILES    = new ArrayList<>();

    public Invoice(int number, String customerEmail) {
        this.number        = number;
        this.customerEmail = customerEmail;
        this.items         = new ArrayList<>();
    }

    public void addItem(String name, double price, int qty) {
        items.add(new InvoiceItem(name, price, qty));
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public double calculateTotal() {
        double subtotal = 0;
        for (InvoiceItem item : items) {
            subtotal += item.price * item.qty;
        }
        if (subtotal > 1000) {
            subtotal = subtotal * 0.95;
        }
        // ضريبة 15%
        subtotal = subtotal * 1.15;
        return subtotal;
    }

    public String printInvoice() {
        StringBuilder sb = new StringBuilder();
        sb.append("INVOICE INV-").append(number).append("\n");
        for (InvoiceItem item : items) {
            sb.append(item.name)
                    .append(" x").append(item.qty)
                    .append(" = ")
                    .append(String.format(Locale.US, "%.2f", item.price * item.qty))
                    .append("\n");
        }
        sb.append("TOTAL: ")
                .append(String.format(Locale.US, "%.2f", calculateTotal()));
        System.out.println(sb.toString());
        return sb.toString();
    }

    public void saveToDatabase(String type) {
        String row = number + ";" + customerEmail + ";"
                + String.format(Locale.US, "%.2f", calculateTotal());
        if ("DATABASE".equals(type)) {
            DATABASE.add(row);
        } else {
            FILES.add(row);
        }
    }

    public String sendEmail() {
        String invoiceText = printInvoice(); // تطبع الفاتورة
        String message = "To: " + customerEmail + "\n"
                + "Subject: Invoice INV-" + number + "\n\n"
                + invoiceText;
        System.out.println("Sending email...");
        return message; // ترجع الرسالة دون طباعتها
    }

    public String sendwhatsapp() {
        String invoiceText = printInvoice(); // تطبع الفاتورة
        String message = "To: sendwhatsapp\n"
                + "Subject: Invoice INV-" + number + "\n\n"
                + invoiceText;
        System.out.println("Sending sendwhatsapp...");
        return message; // ترجع الرسالة دون طباعتها
    }
}