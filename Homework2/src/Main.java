/** Temporary test for Steps 1-2 (will be replaced by the console menu in Step 5). */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Scenario 1: Card payment, full flow ===");
        Order a = new Order(1, 100.0);
        a.setPaymentStrategy(new CardPayment());
        a.pay(); a.ship(); a.deliver();
        System.out.println("Status: " + a.getStatus());

        System.out.println("\n=== Scenario 2: Cash on delivery, full flow ===");
        Order b = new Order(2, 100.0);
        b.setPaymentStrategy(new CashOnDelivery());
        b.pay(); b.ship(); b.deliver();

        System.out.println("\n=== Scenario 3: switching strategy at runtime (same order amount) ===");
        Order c = new Order(3, 100.0);
        c.setPaymentStrategy(new CardPayment());
        c.setPaymentStrategy(new CashOnDelivery());   // changed before paying
        c.pay();
        System.out.printf("Total paid: %.2f%n", c.getPaidAmount());

        System.out.println("\n=== Scenario 4: forbidden transitions ===");
        a.ship();
        a.cancel();
        Order d = new Order(4, 50.0);
        d.pay();      // no payment method chosen
        d.ship();     // not paid
    }
}
