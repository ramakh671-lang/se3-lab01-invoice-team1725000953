package decorator;

import model.Order;

/** Decorator pattern: ConcreteDecorator 1 - adds Logging without touching the original class. */
public class LoggingDecorator extends OrderServiceDecorator {

    public LoggingDecorator(OrderService wrapped) { super(wrapped); }

    @Override
    public Order createOrder(double amount) {
        System.out.println("[LOG] createOrder(amount=" + amount + ") called");
        Order order = super.createOrder(amount);
        System.out.println("[LOG] createOrder -> order #" + order.getId());
        return order;
    }

    @Override
    public Order getOrder(int id) {
        System.out.println("[LOG] getOrder(id=" + id + ") called");
        Order order = super.getOrder(id);
        System.out.println("[LOG] getOrder -> " + (order == null ? "not found" : "order #" + order.getId()));
        return order;
    }
}
