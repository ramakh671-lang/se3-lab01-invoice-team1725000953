package facade;

import model.Order;

/** Facade pattern: subsystem class 1 (in-memory stock). */
public class InventoryService {
    private int stock;

    public InventoryService(int initialStock) { this.stock = initialStock; }

    public boolean checkStock(Order order) {
        boolean available = stock > 0;
        System.out.println("   [Inventory] Order #" + order.getId() + ": "
                + (available ? "item in stock" : "OUT OF STOCK"));
        return available;
    }

    public void reserve(Order order) {
        stock--;
        System.out.println("   [Inventory] Item reserved for order #" + order.getId() + " (remaining: " + stock + ")");
    }
}
