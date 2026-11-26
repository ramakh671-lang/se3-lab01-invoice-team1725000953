/**
 * Adapter pattern: the ADAPTER.
 * Implements the interface the system expects (PaymentStrategy, the Target)
 * and translates the call to the external library (the Adaptee).
 */
public class PayPalAdapter implements PaymentStrategy {
    private final ExternalPayPalLib paypal;
    private final String currency;

    public PayPalAdapter() {
        this(new ExternalPayPalLib(), "USD");
    }

    public PayPalAdapter(ExternalPayPalLib paypal, String currency) {
        this.paypal = paypal;
        this.currency = currency;
    }

    @Override
    public double pay(double amount) {
        // Translate pay(amount) -> makeTransaction(currency, value)
        return paypal.makeTransaction(currency, amount);
    }

    @Override
    public String getName() { return "PayPal"; }
}
