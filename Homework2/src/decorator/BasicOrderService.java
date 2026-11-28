package decorator;

import model.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Decorator pattern: the ConcreteComponent (the ORIGINAL class, never modified).
 * Keeps orders in memory; getOrder simulates a slow database lookup.
 */
public class BasicOrderService implements OrderService {
    private final Map<Integer, Order> database = new HashMap<>();
    private int nextId = 1;

    @Override
    public Order createOrder(double amount) {
        Order order = new Order(nextId++, amount);
        database.put(order.getId(), order);
        return order;
    }

    @Override
    public Order getOrder(int id) {
        System.out.println("   [DB] Slow database lookup for order #" + id + "...");
        return database.get(id);
    }
}
