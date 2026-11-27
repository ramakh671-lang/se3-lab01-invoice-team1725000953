/**
 * Facade pattern: a single simple entry point (placeOrder) that hides the coordination of
 * Inventory, Payment (via the order's PaymentStrategy), Shipping and Notification.
 */
public class CheckoutFacade {
    private final InventoryService inventory = new InventoryService(2);
    private final ShippingService shipping = new ShippingService();
    private final NotificationService notification = new NotificationService();

    /** Returns true if the whole checkout succeeded (the order ends in Shipped state). */
    public boolean placeOrder(Order order) {
        System.out.println(">> Checkout started for order #" + order.getId());

        // 1) Inventory check
        if (!inventory.checkStock(order)) {
            notification.notifyCustomer(order, "Sorry, the item is out of stock.");
            return false;
        }

        // 2) Payment (uses the strategy chosen on the order; State enforces the rules)
        order.pay();
        if (!order.getStatus().equals("Paid")) {
            notification.notifyCustomer(order, "Payment failed, checkout stopped.");
            return false;
        }
        inventory.reserve(order);

        // 3) Shipping (updates the order state to Shipped)
        shipping.arrangeShipping(order);
        order.ship();

        // 4) Notification
        notification.notifyCustomer(order, "Your order was paid and shipped. Total paid: "
                + String.format("%.2f", order.getPaidAmount()));
        System.out.println(">> Checkout finished for order #" + order.getId());
        return true;
    }
}
