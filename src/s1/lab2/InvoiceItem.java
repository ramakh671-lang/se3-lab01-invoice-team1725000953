package s1.lab2;

public class InvoiceItem {
    private final String name;
    private final double price;
    private final int qty;

    public InvoiceItem(String name, double price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQty() { return qty; }
}