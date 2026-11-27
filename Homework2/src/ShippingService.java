/** Facade pattern: subsystem class 2. */
public class ShippingService {
    public void arrangeShipping(Order order) {
        System.out.println("   [Shipping] Package for order #" + order.getId() + " handed to the carrier");
    }
}
