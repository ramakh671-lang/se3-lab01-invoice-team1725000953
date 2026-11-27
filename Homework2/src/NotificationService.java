/** Facade pattern: subsystem class 3. */
public class NotificationService {
    public void notifyCustomer(Order order, String message) {
        System.out.println("   [Notification] Order #" + order.getId() + ": " + message);
    }
}
