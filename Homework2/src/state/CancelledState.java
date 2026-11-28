package state;

import model.Order;

public class CancelledState implements OrderState {
    public void pay(Order o)     { System.out.println("ERROR: order is cancelled."); }
    public void ship(Order o)    { System.out.println("ERROR: order is cancelled."); }
    public void deliver(Order o) { System.out.println("ERROR: order is cancelled."); }
    public void cancel(Order o)  { System.out.println("ERROR: order is already cancelled."); }
    public String getName()      { return "Cancelled"; }
}
