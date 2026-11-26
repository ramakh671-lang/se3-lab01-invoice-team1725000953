/** Strategy pattern: the Strategy interface. */
public interface PaymentStrategy {
    /** Charges the given amount and returns the total actually paid (including fees). */
    double pay(double amount);
    String getName();
}
