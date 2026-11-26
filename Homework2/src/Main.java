/** Temporary test for Steps 1-3 (will be replaced by the console menu in Step 5). */
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
        System.out.println("Status: " + b.getStatus());

        System.out.println("\n=== Scenario 3: PayPal (via Adapter), full flow ===");
        Order p = new Order(3, 100.0);
        p.setPaymentStrategy(new PayPalAdapter());   // used like any other strategy
        p.pay(); p.ship(); p.deliver();
        System.out.println("Status: " + p.getStatus());

        System.out.println("\n=== Scenario 4: switching strategy at runtime (same order amount) ===");
        Order c = new Order(4, 100.0);
        c.setPaymentStrategy(new CardPayment());
        c.setPaymentStrategy(new PayPalAdapter());
        c.setPaymentStrategy(new CashOnDelivery());
        c.pay();
        System.out.printf("Total paid: %.2f%n", c.getPaidAmount());

        System.out.println("\n=== Scenario 5: forbidden transitions ===");
        a.ship();
        a.cancel();
        Order d = new Order(5, 50.0);
        d.pay();      // no payment method chosen
        d.ship();     // not paid
    }
}
