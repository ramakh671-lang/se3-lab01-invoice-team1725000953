/** Temporary test for Steps 1-5 (will be replaced by the console menu in Step 6). */
public class Main {
    public static void main(String[] args) {
        // Decorators stacked on the original service: Logging -> Caching -> Basic
        OrderService service = new LoggingDecorator(new CachingDecorator(new BasicOrderService()));
        CheckoutFacade checkout = new CheckoutFacade();

        System.out.println("=== Decorator: create order ===");
        Order order = service.createOrder(100.0);

        System.out.println("\n=== Decorator: get order twice (Logging + Caching) ===");
        service.getOrder(99);              // not found: cache miss + DB lookup
        service.getOrder(order.getId());   // created order is already cached -> hit
        service.getOrder(order.getId());   // hit again

        System.out.println("\n=== Whole flow with the service ===");
        Order o = service.getOrder(order.getId());
        o.setPaymentStrategy(new PayPalAdapter());
        checkout.placeOrder(o);
        o.deliver();
        System.out.println("Status: " + o.getStatus());

        System.out.println("\n=== Forbidden transition ===");
        o.ship();
    }
}
