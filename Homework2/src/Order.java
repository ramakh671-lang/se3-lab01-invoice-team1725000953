/** State pattern: the Context. It holds the current state and delegates to it. */
public class Order {
    private final int id;
    private OrderState state;

    public Order(int id) {
        this.id = id;
        this.state = new PendingState();
    }

    public void setState(OrderState state) {
        System.out.println("   [Order #" + id + "] " + this.state.getName() + " -> " + state.getName());
        this.state = state;
    }

    // Delegation: Order contains no if/else on the state.
    public void pay()     { state.pay(this); }
    public void ship()    { state.ship(this); }
    public void deliver() { state.deliver(this); }
    public void cancel()  { state.cancel(this); }

    public int getId() { return id; }
    public String getStatus() { return state.getName(); }
}
