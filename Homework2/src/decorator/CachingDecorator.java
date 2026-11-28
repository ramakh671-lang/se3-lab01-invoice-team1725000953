package decorator;

import model.Order;

import java.util.HashMap;
import java.util.Map;

/** Decorator pattern: ConcreteDecorator 2 - adds Caching without touching the original class. */
public class CachingDecorator extends OrderServiceDecorator {
    private final Map<Integer, Order> cache = new HashMap<>();

    public CachingDecorator(OrderService wrapped) { super(wrapped); }

    @Override
    public Order createOrder(double amount) {
        Order order = super.createOrder(amount);
        cache.put(order.getId(), order);
        return order;
    }

    @Override
    public Order getOrder(int id) {
        Order cached = cache.get(id);
        if (cached != null) {
            System.out.println("   [CACHE] Hit for order #" + id);
            return cached;
        }
        System.out.println("   [CACHE] Miss for order #" + id);
        Order order = super.getOrder(id);
        if (order != null) cache.put(id, order);
        return order;
    }
}
