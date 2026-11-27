/** Decorator pattern: the Component interface (the order service). */
public interface OrderService {
    Order createOrder(double amount);
    Order getOrder(int id);
}
