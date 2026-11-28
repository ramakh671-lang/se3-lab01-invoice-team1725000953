package decorator;

import model.Order;

/** Decorator pattern: the base Decorator. Implements the same interface and wraps a component. */
public abstract class OrderServiceDecorator implements OrderService {
    protected final OrderService wrapped;

    protected OrderServiceDecorator(OrderService wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Order createOrder(double amount) { return wrapped.createOrder(amount); }

    @Override
    public Order getOrder(int id) { return wrapped.getOrder(id); }
}
