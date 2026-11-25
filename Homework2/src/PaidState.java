public class PaidState implements OrderState {
    public void pay(Order o)     { System.out.println("ERROR: order is already paid."); }
    public void ship(Order o)    { System.out.println("Order shipped."); o.setState(new ShippedState()); }
    public void deliver(Order o) { System.out.println("ERROR: order must be shipped before delivery."); }
    public void cancel(Order o)  { System.out.println("Order cancelled (refund issued)."); o.setState(new CancelledState()); }
    public String getName()      { return "Paid"; }
}
