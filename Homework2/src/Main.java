import adapter.PayPalAdapter;
import decorator.BasicOrderService;
import decorator.CachingDecorator;
import decorator.LoggingDecorator;
import decorator.OrderService;
import facade.CheckoutFacade;
import model.Order;
import strategy.CardPayment;
import strategy.CashOnDelivery;
import strategy.PaymentStrategy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

/** Console menu that ties the five patterns together. */
public class Main {
    // Decorator: Logging -> Caching -> original service
    private static final OrderService service =
            new LoggingDecorator(new CachingDecorator(new BasicOrderService()));
    // Facade: single entry point for checkout
    private static final CheckoutFacade checkout = new CheckoutFacade();
    private static final Scanner in = new Scanner(System.in);

    // Strategy: payment methods in a map, so no if/else or switch is used to pick one
    private static final Map<Integer, Supplier<PaymentStrategy>> PAYMENT_METHODS = new LinkedHashMap<>();
    static {
        PAYMENT_METHODS.put(1, CardPayment::new);
        PAYMENT_METHODS.put(2, CashOnDelivery::new);
        PAYMENT_METHODS.put(3, PayPalAdapter::new);   // Adapter, used like the others
    }

    public static void main(String[] args) {
        while (true) {   // readInt returns 8 (exit) if the input ends
            printMenu();
            int choice = readInt("Choice: ");
            System.out.println();
            switch (choice) {
                case 1 -> createOrder();
                case 2 -> choosePaymentMethod();
                case 3 -> withOrder(Order::pay);
                case 4 -> withOrder(Order::ship);
                case 5 -> withOrder(Order::deliver);
                case 6 -> withOrder(Order::cancel);
                case 7 -> showStatus();
                case 8 -> { System.out.println("Goodbye!"); return; }
                case 9 -> withOrder(checkout::placeOrder);
                case 10 -> runDemo();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== Order Management ==========");
        System.out.println("1) Create order");
        System.out.println("2) Choose payment method");
        System.out.println("3) Pay");
        System.out.println("4) Ship");
        System.out.println("5) Deliver");
        System.out.println("6) Cancel");
        System.out.println("7) Show status");
        System.out.println("8) Exit");
        System.out.println("9) Checkout (Facade: pay + ship in one call)");
        System.out.println("10) Run demo scenarios");
    }

    private static void createOrder() {
        double amount = readDouble("Order amount: ");
        if (amount <= 0) { System.out.println("Invalid amount."); return; }
        Order order = service.createOrder(amount);
        System.out.println("Order created with id " + order.getId() + " (status: " + order.getStatus() + ")");
    }

    private static void choosePaymentMethod() {
        Order order = findOrder();
        if (order == null) return;
        System.out.println("1) Bank Card   2) Cash On Delivery   3) PayPal");
        Supplier<PaymentStrategy> method = PAYMENT_METHODS.get(readInt("Method: "));
        if (method == null) { System.out.println("Invalid payment method."); return; }
        order.setPaymentStrategy(method.get());
    }

    private static void showStatus() {
        Order order = findOrder();
        if (order == null) return;
        System.out.printf("Order #%d | amount: %.2f | status: %s | paid: %.2f%n",
                order.getId(), order.getAmount(), order.getStatus(), order.getPaidAmount());
    }

    /** Asks for an order id and runs the given action on that order. */
    private static void withOrder(java.util.function.Consumer<Order> action) {
        Order order = findOrder();
        if (order != null) action.accept(order);
    }

    private static Order findOrder() {
        int id = readInt("Order id: ");
        Order order = service.getOrder(id);
        if (order == null) System.out.println("Order not found.");
        return order;
    }

    /** Automatic scenarios for the screenshots: every payment method + forbidden transitions. */
    private static void runDemo() {
        for (Supplier<PaymentStrategy> method : PAYMENT_METHODS.values()) {
            Order o = service.createOrder(100.0);
            System.out.println("\n--- Successful scenario, order #" + o.getId() + " ---");
            o.setPaymentStrategy(method.get());
            o.pay(); o.ship(); o.deliver();
            System.out.println("Final status: " + o.getStatus());
        }

        System.out.println("\n--- Checkout through the Facade (PayPal) ---");
        Order f = service.createOrder(80.0);
        f.setPaymentStrategy(new PayPalAdapter());
        checkout.placeOrder(f);

        System.out.println("\n--- Forbidden transitions (State) ---");
        Order x = service.createOrder(50.0);
        x.ship();      // ship on Pending
        x.cancel();
        x.cancel();    // cancel on Cancelled
        x.pay();       // pay on Cancelled
        System.out.println("Final status: " + x.getStatus());
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        if (!in.hasNextLine()) return 8;
        try { return Integer.parseInt(in.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        if (!in.hasNextLine()) return -1;
        try { return Double.parseDouble(in.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}
