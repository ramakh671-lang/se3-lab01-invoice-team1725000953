public class PendingState implements OrderState {
    public void pay(Order o)     { System.out.println("Payment accepted."); o.setState(new PaidState()); }
    public void ship(Order o)    { System.out.println("ERROR: cannot ship an order that is not paid yet."); }
    public void deliver(Order o) { System.out.println("ERROR: cannot deliver an order that is not paid yet."); }
    public void cancel(Order o)  { System.out.println("Order cancelled."); o.setState(new CancelledState()); }
    public String getName()      { return "Pending"; }
}
