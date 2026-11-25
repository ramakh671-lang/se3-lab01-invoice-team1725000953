/** Temporary test for Step 1 (will be replaced by the console menu in Step 5). */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Scenario 1: valid flow ===");
        Order a = new Order(1);
        a.pay(); a.ship(); a.deliver();
        System.out.println("Status: " + a.getStatus());

        System.out.println("\n=== Scenario 2: forbidden transitions ===");
        a.ship();     // ship on Delivered
        a.cancel();   // cancel on Delivered
        Order b = new Order(2);
        b.ship();     // ship on Pending

        System.out.println("\n=== Scenario 3: cancel ===");
        Order c = new Order(3);
        c.pay(); c.cancel(); c.pay();
        System.out.println("Status: " + c.getStatus());
    }
}
