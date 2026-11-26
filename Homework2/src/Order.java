/**
 * State pattern: the Context (holds the current state).
 * Strategy pattern: also holds the current PaymentStrategy and lets it be switched at runtime.
 */
public class Order {
    private final int id;
    private final double amount;
    private OrderState state;
    private PaymentStrategy paymentStrategy;   // Strategy field
    private double paidAmount;

    public Order(int id, double amount) {
        this.id = id;
        this.amount = amount;
        this.state = new PendingState();
    }

    // ---------- State ----------
    public void setState(OrderState state) {
        System.out.println("   [Order #" + id + "] " + this.state.getName() + " -> " + state.getName());
        this.state = state;
    }

    public void pay()     { state.pay(this); }
    public void ship()    { state.ship(this); }
    public void deliver() { state.deliver(this); }
    public void cancel()  { state.cancel(this); }

    // ---------- Strategy ----------
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
        System.out.println("   [Order #" + id + "] Payment method set to: " + strategy.getName());
    }

    public boolean hasPaymentStrategy() { return paymentStrategy != null; }

    /** Delegates the payment to the current strategy (no if/else or switch on the method). */
    public double charge() {
        paidAmount = paymentStrategy.pay(amount);
        return paidAmount;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public double getPaidAmount() { return paidAmount; }
    public String getStatus() { return state.getName(); }
}
