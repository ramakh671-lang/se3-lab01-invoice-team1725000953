package state;

import model.Order;

public class ShippedState implements OrderState {
    public void pay(Order o)     { System.out.println("ERROR: order is already paid."); }
    public void ship(Order o)    { System.out.println("ERROR: order is already shipped."); }
    public void deliver(Order o) { System.out.println("Order delivered."); o.setState(new DeliveredState()); }
    public void cancel(Order o)  { System.out.println("ERROR: a shipped order cannot be cancelled."); }
    public String getName()      { return "Shipped"; }
}
