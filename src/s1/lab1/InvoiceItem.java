package s1.lab1;

public class InvoiceItem {
    String name;
    double price;
    int qty;

    public InvoiceItem(String name, double price, int qty) {
        this.name  = name;
        this.price = price;
        this.qty   = qty;
    }
}