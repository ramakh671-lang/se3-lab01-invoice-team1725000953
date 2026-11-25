public class DeliveredState implements OrderState {
    public void pay(Order o)     { System.out.println("ERROR: order is already delivered."); }
    public void ship(Order o)    { System.out.println("ERROR: order is already delivered."); }
    public void deliver(Order o) { System.out.println("ERROR: order is already delivered."); }
    public void cancel(Order o)  { System.out.println("ERROR: a delivered order cannot be cancelled."); }
    public String getName()      { return "Delivered"; }
}
