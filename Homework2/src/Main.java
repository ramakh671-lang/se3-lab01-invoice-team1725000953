/** Temporary test for Steps 1-4 (will be replaced by the console menu in Step 5). */
public class Main {
    public static void main(String[] args) {
        CheckoutFacade checkout = new CheckoutFacade();

        System.out.println("=== Scenario 1: Checkout with Card ===");
        Order a = new Order(1, 100.0);
        a.setPaymentStrategy(new CardPayment());
        checkout.placeOrder(a);          // ONE call hides all the services
        a.deliver();
        System.out.println("Status: " + a.getStatus());

        System.out.println("\n=== Scenario 2: Checkout with PayPal (Adapter) ===");
        Order b = new Order(2, 100.0);
        b.setPaymentStrategy(new PayPalAdapter());
        checkout.placeOrder(b);
        System.out.println("Status: " + b.getStatus());

        System.out.println("\n=== Scenario 3: Out of stock ===");
        Order c = new Order(3, 100.0);
        c.setPaymentStrategy(new CashOnDelivery());
        checkout.placeOrder(c);
        System.out.println("Status: " + c.getStatus());

        System.out.println("\n=== Scenario 4: Checkout without a payment method ===");
        CheckoutFacade checkout2 = new CheckoutFacade();
        Order d = new Order(4, 50.0);
        checkout2.placeOrder(d);
        System.out.println("Status: " + d.getStatus());

        System.out.println("\n=== Scenario 5: Forbidden transition ===");
        a.ship();
        a.cancel();
    }
}
